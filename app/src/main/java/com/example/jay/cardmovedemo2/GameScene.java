package com.example.jay.cardmovedemo2;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by jay on 16/3/25.
 */
public class GameScene extends View{

    Context context;

    int width = 93;
    int height = 143;
    int marginLeft = 100;
    int marginTop = 100;
    int marginX = 50;
    int marginY = 300;

    int touchCorrection;

    Rect screen = new Rect(10, 10, 1200, 1200);

    Pile[] pileList = new Pile[7];


    ArrayList<Card> tempList = new ArrayList<Card>();

    int lastIndexOfPile = -1;

    public GameScene(Context context, RelativeLayout relativeLayout, int touchCorrection) {
        super(context);

        this.context = context;
        this.touchCorrection = touchCorrection;

        for(int i = 0;i<7;i++) {
            ImageView pileImg = new ImageView(context);
            pileImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                pileImg.setX(marginLeft+i*(marginX+width));
                pileImg.setY(marginTop);
            }
            else {
                if(i<4){
                    pileImg.setX(marginLeft+i*(marginX+width));
                    pileImg.setY(marginTop);
                }
                else {
                    pileImg.setX(marginLeft+(i-4)*(marginX+width));
                    pileImg.setY(marginTop+height+marginY);
                }
            }
            pileImg.setBackgroundColor(Color.GRAY);
            relativeLayout.addView(pileImg);

            pileList[i] = new Pile(new Point((int)pileImg.getX(), (int)pileImg.getY()));
        }

        Card club1 = new Card(pileList[0], getCardImageView(relativeLayout), 1, Card.Suit.Club, R.drawable.club1);
        Card diamond2 = new Card(pileList[3], getCardImageView(relativeLayout), 2, Card.Suit.Diamond, R.drawable.diamond2);
        Card heart3 = new Card(pileList[6], getCardImageView(relativeLayout), 3, Card.Suit.Heart, R.drawable.heart3);
        Card spade4 = new Card(pileList[3], getCardImageView(relativeLayout), 4, Card.Suit.Spade, R.drawable.spade4);
        Card heart5 = new Card(pileList[6], getCardImageView(relativeLayout),5, Card.Suit.Heart, R.drawable.heart5);
        Card diamond6 = new Card(pileList[0], getCardImageView(relativeLayout),6, Card.Suit.Diamond, R.drawable.diamond6);
        Card club7 = new Card(pileList[6], getCardImageView(relativeLayout),7, Card.Suit.Club, R.drawable.club7);
        Card diamond8 = new Card(pileList[3], getCardImageView(relativeLayout),8, Card.Suit.Diamond, R.drawable.diamond8);
        Card heart9 = new Card(pileList[3], getCardImageView(relativeLayout),9, Card.Suit.Heart, R.drawable.heart9);
        Card spade10 = new Card(pileList[0], getCardImageView(relativeLayout),10, Card.Suit.Spade, R.drawable.spade10);
        Card spade2 = new Card(pileList[6], getCardImageView(relativeLayout),2, Card.Suit.Spade, R.drawable.spade2);
        Card club3 = new Card(pileList[0], getCardImageView(relativeLayout),3, Card.Suit.Club, R.drawable.club3);
        Card club5 = new Card(pileList[6], getCardImageView(relativeLayout),5, Card.Suit.Club, R.drawable.club5);
        Card club6 = new Card(pileList[0], getCardImageView(relativeLayout),6, Card.Suit.Club, R.drawable.club6);
        Card spade12 = new Card(pileList[0], getCardImageView(relativeLayout), 12, Card.Suit.Spade, R.drawable.spade12);
        Card club13 = new Card(pileList[3], getCardImageView(relativeLayout),13, Card.Suit.Club, R.drawable.club13); club13.openCard();
        Card spade8 = new Card(pileList[0], getCardImageView(relativeLayout),8, Card.Suit.Spade, R.drawable.spade8); spade8.openCard();
        Card heart11 = new Card(pileList[6], getCardImageView(relativeLayout),11, Card.Suit.Heart, R.drawable.heart11); heart11.openCard();
    }


    public boolean onTouchEvent(MotionEvent e) {

        int action = MotionEventCompat.getActionMasked(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:


                for (int i = 0; i < pileList.length; i++) {

                    if (pileList[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)) {

                        lastIndexOfPile = i;
                        pileList[i].addCardsSelectedToList(tempList, (int) e.getRawX(), (int) e.getRawY() + touchCorrection);
                        if (tempList.size() != 0) {
                            pileList[i].removeCardsFromCardToLast(tempList.get(0));
                        }

                        break;
                    }

                }

                if (tempList.size() != 0) {
                    for (int i = 0; i < tempList.size(); i++) {
                        tempList.get(i).setLocation((int) e.getRawX() - 50, (int) e.getRawY() + touchCorrection - 50 + 40 * i);
                    }
                }

                return true;

            case MotionEvent.ACTION_MOVE:

                if (screen.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)) {
                    if (tempList.size() != 0) {
                        for (int i = 0; i < tempList.size(); i++) {
                            tempList.get(i).setLocation((int) e.getRawX() - 50, (int) e.getRawY() + touchCorrection - 50 + 40 * i);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                tempList.get(i).img.setElevation(100 + i);
                            }
                        }
                    }

                } else {
                    if (tempList.size() != 0) {
                        pileList[lastIndexOfPile].addCardFromList(tempList);
                        tempList.clear();
                    }
                }

                return true;

            case MotionEvent.ACTION_UP:

                if (screen.contains((int) e.getRawY(), (int) e.getRawY() + touchCorrection)) {

                    if (tempList.size() != 0) {
                        for (int i = 0; i < pileList.length; i++) {
                            if (pileList[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)) {

                                if (pileList[i].addCardFromList(tempList)) {
                                    tempList.clear();
                                    pileList[lastIndexOfPile].openLastCard();
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                        if (tempList.size() != 0) {
                            pileList[lastIndexOfPile].addOriginalCardFromList(tempList);
                            tempList.clear();
                        }
                    }

                } else {
                    if (tempList.size() != 0) {
                        pileList[lastIndexOfPile].addOriginalCardFromList(tempList);
                        tempList.clear();
                    }
                }

                return true;


            default:
                return super.onTouchEvent(e);
        }
    }

    public ImageView getCardImageView(RelativeLayout relativeLayout){
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        relativeLayout.addView(imageView);

        return imageView;
    }

}
