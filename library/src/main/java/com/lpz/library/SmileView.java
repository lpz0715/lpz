package com.lpz.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 笑脸点赞控件
 */
public class SmileView extends LinearLayout implements Animator.AnimatorListener {

    private String defaultLike = "满意";
    //笑脸图片
    private Drawable imageResource;
    //背景
    private Drawable drawableBackground = null;
    private Drawable drawableBackgroundNor = null;
    //笑脸背景
    private int defalutSize = dip2px(getContext(), 25);

    private int textColor = Color.parseColor("#ffffff");

    //笑脸尺寸（大于布局尺寸会显示不全）
    private ImageView imageLike;
    private TextView likeText;
    private LinearLayout likeBack, likeAll;
    private AnimationDrawable animLike; //笑脸帧动画
    private ValueAnimator animatorBack; //背景拉伸动画

    private boolean isClose = false; //判断收起动画
    private int type = 0; //选择执行帧动画的方向 //0 上下 1 左右
    private int iv_height = 50;
    private boolean isChecked = false;

    public SmileView(Context context) {
        this(context, null);
    }

    public SmileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmileView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);

            if (index == R.styleable.SmileView_lpz_iv_background_sel) {
                drawableBackground = typedArray.getDrawable(index);

            } else if (index == R.styleable.SmileView_lpz_iv_background_nor) {
                drawableBackgroundNor = typedArray.getDrawable(index);

            } else if (index == R.styleable.SmileView_lpz_top_txt_color) {
                textColor = typedArray.getColor(index, Color.parseColor("#ffffff"));

            } else if (index == R.styleable.SmileView_lpz_iv_height) {
                iv_height = typedArray.getInt(index, 50);

            } else if (index == R.styleable.SmileView_lpz_iv_type) {
                type = typedArray.getInt(index, 0);

            } else if (index == R.styleable.SmileView_lpz_iv_resource) {
                imageResource = typedArray.getDrawable(index);

            } else if (index == R.styleable.SmileView_lpz_iv_size) {
                int size = typedArray.getInt(index, 25);
                defalutSize = dip2px(context, size);

            } else if (index == R.styleable.SmileView_lpz_top_txt) {
                defaultLike = typedArray.getString(index);

            }
        }

        init();
        bindListener();
    }

    public String getDefaultLike() {
        return defaultLike;
    }

    public Drawable getImageResource() {
        return imageResource;
    }

    public void setImageResource(Drawable imageResource) {
        this.imageResource = imageResource;
    }

    public Drawable getDrawableBackground() {
        return drawableBackground;
    }

    public void setDrawableBackground(Drawable drawableBackground) {
        this.drawableBackground = drawableBackground;
    }

    public Drawable getDrawableBackgroundNor() {
        return drawableBackgroundNor;
    }

    public void setDrawableBackgroundNor(Drawable drawableBackgroundNor) {
        this.drawableBackgroundNor = drawableBackgroundNor;
    }

    public int getDefalutSize() {
        return defalutSize;
    }

    public ImageView getImageLike() {
        return imageLike;
    }

    public void setImageLike(ImageView imageLike) {
        this.imageLike = imageLike;
    }

    public TextView getLikeText() {
        return likeText;
    }

    public void setLikeText(TextView likeText) {
        this.likeText = likeText;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIv_height() {
        return iv_height;
    }

    public void setIv_height(int iv_height) {
        this.iv_height = iv_height;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (animator != null) {
            animator.cancel();
        }
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    public void notifyChange() {
        init();
        bindListener();
        toCheckdeOrNo();
    }

    public void setDefaultLike(String defaultLike) {
        this.defaultLike = defaultLike;

    }


    public void setDefalutSize(int defalutSize) {
        this.defalutSize = defalutSize;
    }

    private void init() {
        this.removeAllViews();
        //初始化总布局
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        setBackgroundColor(Color.TRANSPARENT); //开始透明

        //初始化图片
        imageLike = new ImageView(getContext());
        //添加动画资源  获得帧动画
        imageLike.setBackground(imageResource);
        animLike = (AnimationDrawable) imageLike.getBackground();
        //初始化文字

        likeText = new TextView(getContext());
        likeText.setText(defaultLike);
        likeText.setTextColor(textColor);

        //初始化布局
        likeBack = new LinearLayout(getContext());

        LayoutParams params2 = new LayoutParams(defalutSize, defalutSize);
        likeBack.addView(imageLike, params2);
        if (isChecked) {
            if (drawableBackground == null) {
                likeBack.setBackgroundResource(R.drawable.yellow_background);
            } else {
                likeBack.setBackground(drawableBackground);
            }
        } else {
            if (drawableBackgroundNor == null) {
                likeBack.setBackgroundResource(R.drawable.white_background);
            } else {
                likeBack.setBackground(drawableBackgroundNor);
            }
        }

        //单列总布局
        likeAll = new LinearLayout(getContext());

        likeAll.setOrientation(VERTICAL);

        likeAll.setGravity(Gravity.CENTER_HORIZONTAL);

        likeAll.setBackgroundColor(Color.TRANSPARENT);

        //添加文字图片放进一列
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        params.gravity = Gravity.CENTER;

        likeAll.setGravity(Gravity.RIGHT);
        likeAll.addView(likeText, params);
        likeAll.addView(likeBack, params);


        LayoutParams params3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params3.setMargins(30, 20, 30, defalutBottom);
        params3.gravity = Gravity.BOTTOM;
        addView(likeAll, params3);

        //隐藏文字
        setTxtVisibities(GONE);
    }

    //
    public void setTxtVisibities(int v) {
        likeText.setVisibility(v);
    }

    //绑定监听
    private void bindListener() {

        imageLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                toCheckdeOrNo();
                if (iOnCheckedChangeListner != null) {
                    iOnCheckedChangeListner.checkedChangeListener(isChecked);
                }
            }
        });
    }

    private void toCheckdeOrNo() {
        if (isChecked) {
            animBack(); //拉伸背景
            setTxtVisibities(VISIBLE); //显示文字
            if (drawableBackground == null) {
                likeBack.setBackgroundResource(R.drawable.yellow_background);
            } else {
                likeBack.setBackground(drawableBackground);
            }

        } else {
            if (drawableBackgroundNor == null) {
                likeBack.setBackgroundResource(R.drawable.white_background);
            } else {
                likeBack.setBackground(drawableBackgroundNor);
            }
            //重置帧动画
            imageLike.setBackground(null);
            imageLike.setBackground(imageResource);
            animLike = (AnimationDrawable) imageLike.getBackground();

        }
    }

    //背景伸展动画
    public void animBack() {
        //动画执行中不能点击
        imageLike.setClickable(false);

        animatorBack = ValueAnimator.ofInt(5, iv_height);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int magrin = (int) animation.getAnimatedValue();
                LayoutParams paramsLike
                        = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = magrin;
                imageLike.setLayoutParams(paramsLike);
            }
        });
        isClose = false;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    //背景收回动画
    public void setBackUp() {
        animatorBack = ValueAnimator.ofInt(iv_height, 5);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int magrin = (int) animation.getAnimatedValue();
                LayoutParams paramsLike
                        = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = magrin;

                imageLike.setLayoutParams(paramsLike);

            }
        });
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //重置帧动画
        animLike.stop();

        //关闭时不执行帧动画
        if (isClose) {
            //收回后可点击
            imageLike.setClickable(true);
            //隐藏文字
            setTxtVisibities(GONE);
            //恢复透明
//            setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        isClose = true;


        if (type == 0) {
            animLike.start();
            objectY(imageLike);
        } else {
            objectX(imageLike);
        }
    }

    private ObjectAnimator animator;

    public void objectY(View view) {
        animator = ObjectAnimator.ofFloat(view, "translationY", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        //animator.setRepeatCount(1);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }
        });
    }

    private ObjectAnimator animator2;

    public void objectX(View view) {

        animator2 = ObjectAnimator.ofFloat(view, "translationX", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator2.setRepeatMode(ObjectAnimator.RESTART);
        // animator.setRepeatCount(1);
        animator2.setDuration(1500);
        animator2.start();

        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }
        });
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    //dp转px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private IOnCheckedChangeListener iOnCheckedChangeListner;

    public void onSmileViewCheckedChangeListner(IOnCheckedChangeListener iOnCheckedChangeListner) {
        this.iOnCheckedChangeListner = iOnCheckedChangeListner;
    }
}