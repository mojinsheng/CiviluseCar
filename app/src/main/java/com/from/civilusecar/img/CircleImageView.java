package com.from.civilusecar.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能描述：一个简洁而高效的圆形ImageView
 *
 * @author (作者) edward（冯丰枫）
 * @link http://www.jianshu.com/u/f7176d6d53d2
 * 创建时间： 2018/4/17 0017
 */
public class CircleImageView extends AppCompatImageView {
    private float width;
    private float height;
    private float radius;
    private Paint paint;
    private Matrix matrix;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿
        matrix = new Matrix();      //初始化缩放矩阵
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        if (drawable instanceof BitmapDrawable) {
            paint.setShader(initBitmapShader((BitmapDrawable) drawable));//将着色器设置给画笔
            canvas.drawCircle(width / 2, height / 2, radius, paint);//使用画笔在画布上画圆
            return;
        }
        super.onDraw(canvas);
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
