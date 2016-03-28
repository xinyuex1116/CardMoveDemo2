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
    int marginTop = 300;
    int marginX = 50;
    int marginY = 300;

    int touchCorrection;

    Rect screen = new Rect(10, 10, 1200, 1200);

    BasicPile[] basicPileList = new BasicPile[7];

    StockAndWaste stockAndWaste;

    FoundationPile[] foundationPiles = new FoundationPile[4];

    ArrayList<Card> tempList = new ArrayList<Card>();

    int lastIndexOfPile = -1;

    boolean flip = false;
    boolean reset = false;
    boolean isFromWaste = false;
    int fromIndexOfFoundation = -1;

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

            basicPileList[i] = new BasicPile(new Point((int)pileImg.getX(), (int)pileImg.getY()));
        }

        ImageView stockImg = new ImageView(context);
        stockImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        stockImg.setBackgroundColor(Color.GRAY);
        stockImg.setX(100);
        stockImg.setY(70);
        relativeLayout.addView(stockImg);

        ImageView wasteImg = new ImageView(context);
        wasteImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        wasteImg.setBackgroundColor(Color.GRAY);
        wasteImg.setX(100 + marginX + width);
        wasteImg.setY(70);
        relativeLayout.addView(wasteImg);

        stockAndWaste = new StockAndWaste(new Point((int)stockImg.getX(), (int)stockImg.getY()), new Point((int)wasteImg.getX(), (int)wasteImg.getY()));

        ImageView heartFoundationPileImg = new ImageView(context);
        heartFoundationPileImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        heartFoundationPileImg.setBackgroundColor(Color.GRAY);
        heartFoundationPileImg.setX(100 + marginX * 2 + width * 2);
        heartFoundationPileImg.setY(70);
        relativeLayout.addView(heartFoundationPileImg);
        foundationPiles[0] = new FoundationPile(new Point((int)heartFoundationPileImg.getX(), (int)heartFoundationPileImg.getY()), Card.Suit.Heart);

        ImageView clubFoundationPileImg = new ImageView(context);
        clubFoundationPileImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        clubFoundationPileImg.setBackgroundColor(Color.GRAY);
        clubFoundationPileImg.setX(heartFoundationPileImg.getX() + 40 + width);
        clubFoundationPileImg.setY(70);
        relativeLayout.addView(clubFoundationPileImg);
        foundationPiles[1] = new FoundationPile(new Point((int)clubFoundationPileImg.getX(), (int)clubFoundationPileImg.getY()), Card.Suit.Club);

        ImageView spadeFoundationPileImg = new ImageView(context);
        spadeFoundationPileImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        spadeFoundationPileImg.setBackgroundColor(Color.GRAY);
        spadeFoundationPileImg.setX(clubFoundationPileImg.getX() + 40 + width);
        spadeFoundationPileImg.setY(70);
        relativeLayout.addView(spadeFoundationPileImg);
        foundationPiles[2] = new FoundationPile(new Point((int)spadeFoundationPileImg.getX(), (int)spadeFoundationPileImg.getY()), Card.Suit.Heart);

        ImageView diamondFoundationPileImg = new ImageView(context);
        diamondFoundationPileImg.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        diamondFoundationPileImg.setBackgroundColor(Color.GRAY);
        diamondFoundationPileImg.setX(spadeFoundationPileImg.getX() + 40 + width);
        diamondFoundationPileImg.setY(70);
        relativeLayout.addView(diamondFoundationPileImg);
        foundationPiles[3] = new FoundationPile(new Point((int)diamondFoundationPileImg.getX(), (int)diamondFoundationPileImg.getY()), Card.Suit.Diamond);

        Card club1 = new Card(stockAndWaste, getCardImageView(relativeLayout), 1, Card.Suit.Club, R.drawable.club1);
        Card diamond2 = new Card(stockAndWaste, getCardImageView(relativeLayout), 2, Card.Suit.Diamond, R.drawable.diamond2);
        Card heart3 = new Card(stockAndWaste, getCardImageView(relativeLayout), 3, Card.Suit.Heart, R.drawable.heart3);
        Card spade4 = new Card(stockAndWaste, getCardImageView(relativeLayout), 4, Card.Suit.Spade, R.drawable.spade4);
        Card heart5 = new Card(stockAndWaste, getCardImageView(relativeLayout),5, Card.Suit.Heart, R.drawable.heart5);
        Card diamond6 = new Card(stockAndWaste, getCardImageView(relativeLayout),6, Card.Suit.Diamond, R.drawable.diamond6);
        Card club7 = new Card(stockAndWaste, getCardImageView(relativeLayout),7, Card.Suit.Club, R.drawable.club7);
        Card diamond8 = new Card(stockAndWaste, getCardImageView(relativeLayout),8, Card.Suit.Diamond, R.drawable.diamond8);
        Card heart9 = new Card(stockAndWaste, getCardImageView(relativeLayout),9, Card.Suit.Heart, R.drawable.heart9);
        Card spade10 = new Card(basicPileList[2], getCardImageView(relativeLayout),10, Card.Suit.Spade, R.drawable.spade10); spade10.openCard();
        Card spade2 = new Card(stockAndWaste, getCardImageView(relativeLayout),2, Card.Suit.Spade, R.drawable.spade2);
        Card club3 = new Card(basicPileList[1], getCardImageView(relativeLayout),3, Card.Suit.Club, R.drawable.club3); club3.openCard();
        Card club5 = new Card(basicPileList[0], getCardImageView(relativeLayout),5, Card.Suit.Club, R.drawable.club5);
        Card club6 = new Card(stockAndWaste, getCardImageView(relativeLayout),6, Card.Suit.Club, R.drawable.club6);
        Card spade12 = new Card(stockAndWaste, getCardImageView(relativeLayout), 12, Card.Suit.Spade, R.drawable.spade12);
        Card club13 = new Card(basicPileList[3], getCardImageView(relativeLayout),13, Card.Suit.Club, R.drawable.club13); club13.openCard();
        Card spade8 = new Card(basicPileList[0], getCardImageView(relativeLayout),8, Card.Suit.Spade, R.drawable.spade8); spade8.openCard();
        Card heart11 = new Card(basicPileList[6], getCardImageView(relativeLayout),11, Card.Suit.Heart, R.drawable.heart11); heart11.openCard();
    }


    public boolean onTouchEvent(MotionEvent e) {

        int action = MotionEventCompat.getActionMasked(e);

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                if(stockAndWaste.stockArea.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)){
                    if(stockAndWaste.stockList.size()!=0){
                        flip = true;
                    }
                    else {
                        reset = true;
                    }
                }
                else if(stockAndWaste.wasteArea.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)&&stockAndWaste.wasteList.size()!=0){

                    stockAndWaste.addCardToTempList(tempList);
                    isFromWaste = true;

                }



                for(int i = 0; i < foundationPiles.length; i++){
                    if(foundationPiles[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)){
                        if(foundationPiles[i].card!=null){
                            foundationPiles[i].addCardToTemplist(tempList);
                            fromIndexOfFoundation = i;
                        }
                    }
                }

                for (int i = 0; i < basicPileList.length; i++) {

                    if (basicPileList[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)) {

                        lastIndexOfPile = i;
                        basicPileList[i].addCardsSelectedToList(tempList, (int) e.getRawX(), (int) e.getRawY() + touchCorrection);
                        if (tempList.size() != 0) {
                            basicPileList[i].removeCardsFromCardToLast(tempList.get(0));
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
                        basicPileList[lastIndexOfPile].addCardFromList(tempList);
                        tempList.clear();
                    }
                }

                return true;

            case MotionEvent.ACTION_UP:

                if(stockAndWaste.stockArea.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)){
                    if(reset==true){
                        stockAndWaste.reset();
                        reset = false;
                    }
                    else if(flip==true){
                        stockAndWaste.flip();
                        flip = false;
                    }
                }


                if (screen.contains((int) e.getRawY(), (int) e.getRawY() + touchCorrection)) {

                    if (tempList.size() != 0) {

                        for(int i = 0; i<foundationPiles.length;i++){
                            if(foundationPiles[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)&&foundationPiles[i].addCardFromTempList(tempList)){
                                tempList.clear();
                                isFromWaste=false;
                                fromIndexOfFoundation = -1;

                                break;
                            }
                        }

                        for (int i = 0; i < basicPileList.length; i++) {
                            if (basicPileList[i].area.contains((int) e.getRawX(), (int) e.getRawY() + touchCorrection)) {

                                if (basicPileList[i].addCardFromList(tempList)) {
                                    tempList.clear();
                                    if(lastIndexOfPile!=-1) {
                                        basicPileList[lastIndexOfPile].openLastCard();
                                        lastIndexOfPile = -1;
                                    }
                                    else if(isFromWaste==true){
                                        isFromWaste = false;
                                    }
                                    else if(fromIndexOfFoundation != -1){
                                        fromIndexOfFoundation = -1;
                                    }


                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                        returnCardsBack();
                    }

                } else {
                    returnCardsBack();
                }

                return true;


            default:
                return super.onTouchEvent(e);
        }
    }

    public void returnCardsBack(){
        if (tempList.size() != 0) {
            if(lastIndexOfPile!=-1) {
                basicPileList[lastIndexOfPile].returnCards(tempList);
                tempList.clear();
                lastIndexOfPile=-1;
            }
            else if(isFromWaste==true){
                stockAndWaste.returnCards(tempList);
                tempList.clear();
                isFromWaste = false;
            }
            else if(fromIndexOfFoundation!=-1){
                foundationPiles[fromIndexOfFoundation].returnCards(tempList);
                tempList.clear();
                fromIndexOfFoundation = -1;
            }
        }
    }

    public ImageView getCardImageView(RelativeLayout relativeLayout){
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        relativeLayout.addView(imageView);

        return imageView;
    }

}
