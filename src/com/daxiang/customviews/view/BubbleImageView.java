package com.daxiang.customviews.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.daxiang.customviews.R;
import com.daxiang.customviews.utils.Logger;

/**
 * 带指示箭头的ImageView，比如聊天的气泡；
 * 
 * @author daxiang
 * @date 2017-10-7
 */
public class BubbleImageView extends ImageView {

	private static final String TAG = BubbleImageView.class.getSimpleName();

	private Paint mImagePaint;
	private Drawable mDrawable;
	private Path mPath;
	private BitmapShader mShader;
	private int mArrowWidth;
	private int mArrowHeight;
	private int mArrowStartMargin;
	private Matrix mMatrix;

	@SuppressLint("NewApi")
	public BubbleImageView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr, defStyleRes);
	}

	public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle, 0);
	}

	public BubbleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0, 0);
	}

	public BubbleImageView(Context context) {
		super(context);
		init(context, null, 0, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		mImagePaint = new Paint();
		mImagePaint.setAntiAlias(true);
		mPath = new Path();
		mArrowWidth = 30;
		mArrowHeight = 20;
		mArrowStartMargin = -1;
		mMatrix = new Matrix();

		if (attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs,
					R.styleable.BubbleImageView, defStyleAttr, defStyleRes);
			mArrowStartMargin = typedArray.getDimensionPixelSize(
					R.styleable.BubbleImageView_arrowStartMargin,
					mArrowStartMargin);
			mArrowWidth = typedArray.getDimensionPixelSize(
					R.styleable.BubbleImageView_arrowWidth, mArrowWidth);
			mArrowHeight = typedArray.getDimensionPixelSize(
					R.styleable.BubbleImageView_arrowHeight, mArrowHeight);

			typedArray.recycle();
		}
		if (mArrowWidth < 0 || mArrowHeight < 0) {
			throw new RuntimeException(
					"Arrow width and height must be positive!");
		}
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mDrawable = getDrawable();
		onImageDrawableReset();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		mDrawable = getDrawable();
		onImageDrawableReset();
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mDrawable = getDrawable();
		onImageDrawableReset();
	}

	private void onImageDrawableReset() {
		mShader = null;
		if (mImagePaint != null)
			mImagePaint.setShader(null);
	}

	protected Bitmap getBitmap() {
		if (mDrawable != null && mDrawable instanceof BitmapDrawable) {

			return ((BitmapDrawable) mDrawable).getBitmap();
		}

		return null;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Logger.e(TAG, "onSizeChanged  width=" + w + "  height=" + h);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	protected void onDraw(Canvas canvas) {

		if (mShader == null) {
			Bitmap bitmap = getBitmap();
			if (bitmap != null && bitmap.getWidth() > 0
					&& bitmap.getHeight() > 0) {
				mShader = new BitmapShader(bitmap, TileMode.CLAMP,
						TileMode.CLAMP);
				float scaleX = (float) getWidth() / bitmap.getWidth();
				float scaleY = (float) getHeight() / bitmap.getHeight();
				mMatrix.setScale(scaleX, scaleY);
				mShader.setLocalMatrix(mMatrix);
				mImagePaint.setShader(mShader);
			}

		}

		if (mShader != null) {

			mPath.reset();
			mPath.addRect(0, mArrowHeight, getWidth(), getHeight(),
					Path.Direction.CW);
			if (mArrowStartMargin < 0) {// 默认箭头居中；
				mPath.moveTo(getWidth() / 2, 0);
				mPath.lineTo((getWidth() - mArrowWidth) / 2, mArrowHeight);
				mPath.lineTo((getWidth() + mArrowWidth) / 2, mArrowHeight);
				mPath.lineTo(getWidth() / 2, 0);
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
					&& getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
				// 阿拉伯语环境下布局是从右到左；
				mPath.moveTo(getWidth() - mArrowStartMargin - mArrowWidth / 2,
						0);
				mPath.lineTo(getWidth() - mArrowStartMargin, mArrowHeight);
				mPath.lineTo(getWidth() - mArrowStartMargin - mArrowWidth,
						mArrowHeight);
				mPath.moveTo(getWidth() - mArrowStartMargin - mArrowWidth / 2,
						0);
			} else {
				// 从左到右
				mPath.moveTo(mArrowStartMargin + mArrowWidth / 2, 0);
				mPath.lineTo(mArrowStartMargin, mArrowHeight);
				mPath.lineTo(mArrowStartMargin + mArrowWidth, mArrowHeight);
				mPath.lineTo(mArrowStartMargin + mArrowWidth / 2, 0);
			}

			// canvas.drawRect(40, 40, getWidth(), getHeight(), mImagePaint);
			canvas.drawPath(mPath, mImagePaint);
		} else {
			super.onDraw(canvas);
		}

	}

}
