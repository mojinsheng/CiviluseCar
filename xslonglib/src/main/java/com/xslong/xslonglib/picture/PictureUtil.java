package com.xslong.xslonglib.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PictureUtil {


	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {
		Bitmap bm = getSmallBitmap(filePath);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
//			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}
		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}


	public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(dp2px(context, size));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return drawTextToBitmap(context, bitmap, text, paint, bounds,
				dp2px(context, paddingLeft),
				dp2px(context, paddingTop) + bounds.height());
	}


	/**
	 * 绘制文字到右下角
	 * @param context
	 * @param bitmap
	 * @param text
	 * @param size
	 * @param color
	 * @param paddingRight
	 * @param paddingBottom
	 * @return
	 */
	public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(dp2px(context, size));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);

		return drawTextToBitmap(context, bitmap, text, paint, bounds,
				bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
				bitmap.getHeight() - dp2px(context, paddingBottom));
	}

	//图片上绘制文字
	private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
		Bitmap.Config bitmapConfig = bitmap.getConfig();

		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawText(text, paddingLeft, paddingTop, paint);
		return bitmap;
	}

	// 加水印 也可以加文字
	public static Bitmap watermarkBitmap(Bitmap src, int textSize,
                                         String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		Canvas cv = new Canvas(src);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		//加入文字
		if(title!=null) {
			String familyName ="宋体";
			Typeface font = Typeface.create(familyName, Typeface.NORMAL);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(textSize);
			//这里是自动换行的
			StaticLayout layout = new StaticLayout(title, textPaint,
					w, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F,true);
			layout.draw(cv);
			//文字就加左上角算了
//			cv.drawText(title, 0, 100, paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return src;
	}

    /**
	 * 添加文字水印
	 *
	 * @param src      源图片
	 * @param content  水印文本
	 * @param textSize 水印字体大小
	 * @param x        起始坐标x
	 * @param y        起始坐标y
	 * @return 带有文字水印的图片
	 */
	public static Bitmap addTextWatermark(Bitmap src, String content, int textSize, float x,
										  float y) {
		return addTextWatermark(src, content, textSize, Color.RED, x, y, false);
	}

	/**
	 * 添加文字水印
	 *
//	 * @param src      源图片
	 * @param content  水印文本
	 * @param textSize 水印字体大小
	 * @param color    水印字体颜色
	 * @param x        起始坐标x
	 * @param y        起始坐标y
	 * @param recycle  是否回收
	 * @return 带有文字水印的图片
	 */
	public static Bitmap addTextWatermark(Bitmap ret, String content, float textSize, int color, float x,
										  float y, boolean recycle) {
		if (isEmptyBitmap(ret) || content == null) return null;
//		Bitmap ret = src.copy(src.getConfig(), true);

		TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		Canvas canvas = new Canvas(ret);
		paint.setColor(color);
		paint.setTextSize(textSize);
		Rect bounds = new Rect();
		paint.getTextBounds(content, 0, content.length(), bounds);
		canvas.drawText(content, x, y + textSize, paint);
		if (recycle && !ret.isRecycled()) ret.recycle();
		return ret;
	}

	/**
	 * 按质量压缩
	 *
	 * @param src         源图片
	 * @param maxByteSize 允许最大值字节数
	 * @return 质量压缩压缩过的图片
	 */
	public static Bitmap compressByMaxByte(Bitmap src, long maxByteSize,ByteArrayOutputStream os) {
		return compressByMaxByte(src, maxByteSize,os, false);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src         源图片
	 * @param maxByteSize 允许最大值字节数
	 * @param recycle     是否回收
	 * @return 质量压缩压缩过的图片
	 */
	public static Bitmap compressByMaxByte(Bitmap src, long maxByteSize, ByteArrayOutputStream os,boolean recycle) {
		if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
		int quality = 100;
		src.compress(Bitmap.CompressFormat.JPEG, quality, os);
		while (os.toByteArray().length > maxByteSize && quality > 0) {
			os.reset();
			src.compress(Bitmap.CompressFormat.JPEG, quality -= 5, os);
		}
		if (quality < 0) return null;
		byte[] bytes = os.toByteArray();
		if (recycle && !src.isRecycled()) src.recycle();
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src     源图片
	 * @param quality 质量
	 * @return 质量压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, int quality,ByteArrayOutputStream os) {
		return compressByQuality(src, quality, os,false);
	}
	/**
	 * 按质量压缩
	 *
	 * @param src     源图片
	 * @param quality 质量
	 * @param recycle 是否回收
	 * @return 质量压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, int quality, ByteArrayOutputStream baos , boolean recycle) {
		if (isEmptyBitmap(src) || quality < 0 || quality > 100) return null;
		src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] bytes = baos.toByteArray();
		if (recycle && !src.isRecycled()) src.recycle();
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 判断bitmap对象是否为空
	 *
	 * @param src 源图片
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	private static boolean isEmptyBitmap(Bitmap src) {
		return src == null || src.getWidth() == 0 || src.getHeight() == 0;
	}

	/**
	 * dip转pix
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}


	/**
	 * 获取保存图片的目录
	 * @return
	 */
	public static File getAlbumDir() {
		File dir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "tower";
	}
}
