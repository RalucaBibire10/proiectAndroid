package com.example.whitetile;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Tile {
    private MediaPlayer mediaPlayer;
    private ObjectAnimator animator;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private boolean isCanceled;

    public Tile(MediaPlayer mediaPlayer, ObjectAnimator animator, ImageView imageView, RelativeLayout relativeLayout) {
        this.mediaPlayer = mediaPlayer;
        this.animator = animator;
        this.imageView = imageView;
        this.relativeLayout = relativeLayout;
        isCanceled = false;
    }

    public boolean getIsCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public ObjectAnimator getAnimator() {
        return animator;
    }

    public void setAnimator(ObjectAnimator animator) {
        this.animator = animator;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    public void cancelAnimator() {
        animator.cancel();
        isCanceled = true;
    }
}
