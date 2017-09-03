package com.daxiang.customviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局；
 * 
 * @author daxiang
 * @date 2017-7-2
 */
public class FlowLayout extends ViewGroup {

	public FlowLayout(Context context) {
		super(context);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
		int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

		int width = 0;
		int height = 0;
		int lineWidth = 0;
		int lineHeight = 0;
		int count = getChildCount();
		View childView;
		for (int i = 0; i < count; i++) {
			childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, heightMeasureSpec);

			MarginLayoutParams lp = null;
			if (childView.getLayoutParams() instanceof MarginLayoutParams) {
				lp = (MarginLayoutParams) childView.getLayoutParams();
			} else {
				lp = new MarginLayoutParams(0, 0);
			}

			int childWidth = childView.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			int childHeight = childView.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;

			if (lineWidth + childWidth > measureWidth) {// 换行
				width = Math.max(width, lineWidth);
				height += lineHeight;

				lineWidth = childWidth;
				lineHeight = childHeight;

			} else {// 不换行
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}

			if (i == count - 1) {
				width = Math.max(width, lineWidth);
				height += lineHeight;
			}
		}

		setMeasuredDimension(
				measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width,
				measureHeightMode == MeasureSpec.EXACTLY ? measureHeight
						: height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int count = getChildCount();
		View child;
		int top = 0, left = 0;
		int lineWidth = 0;
		int lineHeight = 0;
		int childWidth = 0;
		int childHeight = 0;
		for (int i = 0; i < count; i++) {
			child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;
			if (childWidth + lineWidth > getMeasuredWidth()) {
				// 如果换行,当前控件将跑到下一行，从最左边开始，所以left就是0，而top则需要加上上一行的行高，才是这个控件的top点;
				top += lineHeight;
				left = 0;
				// 同样，重新初始化lineHeight和lineWidth
				lineHeight = childHeight;
				lineWidth = childWidth;
			} else {
				lineHeight = Math.max(lineHeight, childHeight);
				lineWidth += childWidth;
			}
			// 计算childView的left,top,right,bottom
			int lc = left + lp.leftMargin;
			int tc = top + lp.topMargin;
			int rc = lc + child.getMeasuredWidth();
			int bc = tc + child.getMeasuredHeight();
			Log.i("FlowLayout", "ChildView的top=" + top);
			child.layout(lc, tc, rc, bc);
			// 将left置为下一子控件的起始点
			left += childWidth;
		}
	}

}
