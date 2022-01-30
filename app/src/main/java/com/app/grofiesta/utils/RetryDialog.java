package com.app.grofiesta.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.grofiesta.R;

public class RetryDialog extends android.app.Dialog {
    View view;
    View backView;
    Context context;
    String message;
    int iconType;
    TextView messageText;
    ImageView imgIcon;
    LottieAnimationView animation_view;
    String title;
    TextView titleText;

    TextView retryBtn;
    TextView btnRetryOk;

    public static final int NO_INTERNET = 1;
    public static final int SERVER_ERROR = 2;

    View.OnClickListener onRetryButtonClickListener;

    public RetryDialog(Context context, int iconType) {
        super(context, android.R.style.Theme_Translucent);
        this.context = context;
        this.iconType = iconType;
    }

    public RetryDialog(Context context, String title, String message, int iconType) {
        super(context, android.R.style.Theme_Translucent);
        this.context = context;
        this.message = message;
        this.title = title;
        this.iconType = iconType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_not_connect_layout);
        view = (LinearLayout) findViewById(R.id.contentDialog);
        backView = (LinearLayout) findViewById(R.id.dialog_rootView);

        titleText = (TextView) findViewById(R.id.title);
        setTitle(title);

        messageText = (TextView) findViewById(R.id.message);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        animation_view = (LottieAnimationView) findViewById(R.id.animation_view);
        setMessage(message);

        if (iconType == NO_INTERNET) {
            animation_view.setAnimation(R.raw.no_internet_connection_new);
//            imgIcon.setImageResource(R.drawable.no_internet);
            titleText.setText(context.getString(R.string.internet_not_connect));
            messageText.setText(context.getString(R.string.internet_not_message));
        } else if (iconType == SERVER_ERROR) {
            animation_view.setAnimation(R.raw.server_error);
//            imgIcon.setImageResource(R.drawable.ic_oops_sorry_illustration);
            titleText.setText(context.getString(R.string.server_error_title));
            messageText.setText(context.getString(R.string.server_error_message_new));
        }

        retryBtn = (TextView) findViewById(R.id.btnRetry);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onRetryButtonClickListener != null)
                    onRetryButtonClickListener.onClick(v);
            }
        });
        btnRetryOk = (TextView) findViewById(R.id.btnRetryOk);
        btnRetryOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                /*if (onRetryButtonClickListener != null)
                    onRetryButtonClickListener.onClick(v);*/
            }
        });


    }

    @Override
    public void show() {
        super.show();
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
        backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }


    public void setMessage(String message) {
        this.message = message;
        messageText.setText(message);
    }

    public void setTitle(String title) {
        this.title = title;
        titleText.setVisibility(View.VISIBLE);
        titleText.setText(title);
    }

    public void setOnRetryButtonClickListener(
            View.OnClickListener onAcceptButtonClickListener) {
        this.onRetryButtonClickListener = onAcceptButtonClickListener;
        if (retryBtn != null)
            retryBtn.setOnClickListener(onAcceptButtonClickListener);
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        RetryDialog.super.dismiss();
                    }
                });

            }
        });
        Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);

        view.startAnimation(anim);
        backView.startAnimation(backAnim);
    }

    @Override
    public void onBackPressed() {
        super.setCancelable(false);

    }
}