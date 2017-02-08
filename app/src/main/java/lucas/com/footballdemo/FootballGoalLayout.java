package lucas.com.footballdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 顶部足球射门绘制视图
 */

public class FootballGoalLayout extends RelativeLayout {
    private Activity activity;
    private float xy;
    private double proportionX;
    private int stateX;
    private double proportionY;
    private int stateY;
    private ShootEntity entity;
    private TranslateAnimation animationBall;


    public FootballGoalLayout(Context context) {
        super(context);
    }

    public FootballGoalLayout(Context contexts, AttributeSet attrs) {
        super(contexts, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    private Paint paint = new Paint();
    private int width;
    private int height;
    private int ballWidth = 48;
    private int playerWidth = 120;
    private int goalWidth = 300;
    private int ballXPos;
    private int ballYPos;
    private int frameXPos;
    private int frameYPos;
    private int gkXPos;
    private int gkYPos;
    private int finalX;
    private int finalY;
    private int finalPX;
    private int finalPY;
    private int res;
    private int leftP;
    private int rightP;
    private int topP;
    private int bottomP;
    private String isOK;
    private int resG;
    private int finalWidth;
    private int finalHeight;
    private ImageView restart;
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        paint.setColor(Color.WHITE);
        
        
    }
    
    public void goal(ShootEntity entity,Activity activity){
        removeAllViews();
        this.activity = activity;
        this.entity = entity;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = ScreenUtils.getScreenWidth(activity)-ScreenUtils.dip2px(activity, 100);
        //球场角球点起算
        params.height = params.width*768/1024;
        width = params.width;
        height = params.height;
        setLayoutParams(params);
        xy = (float)MyBigDecimal.div(1024,params.width);
        proportionX = MyBigDecimal.div(732, 1024);
        stateX = ((int)(params.width-MyBigDecimal.mul(params.width, proportionX))+1)/2;
        proportionY = MyBigDecimal.div(244, 768);
        stateY = ((int)(params.height-MyBigDecimal.mul(params.height, proportionY))+1)/2;
        restart = (ImageView)activity.findViewById(R.id.restart);
        
//        if (entityText == null) {
            ImageView ivBall = new ImageView(activity);
            addView(ivBall,generateDefaultLayoutParams());
            ImageView ivPlayer = new ImageView(activity);
            addView(ivPlayer,generateDefaultLayoutParams());
            ImageView ivGoal = new ImageView(activity);
            addView(ivGoal,generateDefaultLayoutParams());
//        } else {
//        }
//        entityText = entity;
        setView();
    }

    private void setView() {
        ballXPos = entity.getBallXPos();
        ballYPos = entity.getBallYPos();
        frameXPos = entity.getFrameXPos();
        frameYPos = entity.getFrameYPos();
        gkXPos = entity.getGkXPos();
        gkYPos = entity.getGkYPos();
        isOK = entity.getIsOK();
        
        ImageView ivBall = (ImageView)getChildAt(0);
        ivBall.clearAnimation();
        
        ivBall.setBackgroundResource(R.mipmap.soccer_b03_hdpi);
        int leftB = width/2-ballWidth/2;
        int rightB = leftB+ballWidth;
        int topB = height/10*9-ballWidth/2;
        int bottomB = topB+ballWidth;
        ivBall.layout(leftB, topB, rightB, bottomB);
        finalX = (int)MyBigDecimal.div(frameXPos,xy)+stateX-ballWidth/2;
        finalY = (int)MyBigDecimal.div(frameYPos,xy)+stateY-ballWidth/2;
        animationBall = new TranslateAnimation( 0,-(leftB-finalX),0,-(topB-finalY));
        animationBall.setDuration(1000);
        animationBall.setFillAfter(true);
        
        ImageView ivPlayer = (ImageView)getChildAt(1);
        ivPlayer.clearAnimation();
        
        finalPX = (int)MyBigDecimal.div(gkXPos,xy)+stateX-playerWidth/2;
        finalPY = (int)MyBigDecimal.div(gkYPos,xy)+stateY-playerWidth/2;
        finalWidth = (int)MyBigDecimal.div(gkXPos,xy)+stateX;
        finalHeight = (int)MyBigDecimal.div(gkYPos,xy)+stateY;
        
        
        leftP = width/2-playerWidth/2;
        topP = height/2+playerWidth/4;
        
        int dWidth = (width-2*stateX);
        int dHeight = (height-2*stateY);
        if(finalWidth<=(dWidth/3+stateX) && finalHeight<(dHeight/2+stateY)){
            res = R.mipmap.zuoshang;
            rightP = leftP+playerWidth;
            bottomP = topP+playerWidth*5/4;
        }else if(finalWidth>(dWidth/3+stateX) && finalWidth<=(dWidth/3*2+stateX) && finalHeight<(dHeight/2+stateY)){
            res = R.mipmap.shang;
            rightP = leftP+playerWidth;
            bottomP = topP+playerWidth*5/3;
        }else if(finalWidth>(dWidth/3*2+stateX) && finalHeight<(dHeight/2+stateY)){
            res = R.mipmap.youshang;
            rightP = leftP+playerWidth;
            bottomP = topP+playerWidth*5/4;
        }else if(finalWidth<=(dWidth/3+stateX) && finalHeight>=(dHeight/2+stateY)){
            res = R.mipmap.zuoxia;
            rightP = leftP+playerWidth/3*4;
            bottomP = topP+playerWidth/3*2;
        }else if(finalWidth>(dWidth/3+stateX) && finalWidth<=(dWidth/3*2+stateX) && finalHeight>=(dHeight/2+stateY)){
            res = R.mipmap.zhongjian;
            rightP = leftP+playerWidth;
            bottomP = topP+playerWidth;
        }else if(finalWidth>(dWidth/3*2+stateX) && finalHeight>=(dHeight/2+stateY)){
            res = R.mipmap.youxia;
            rightP = leftP+playerWidth/3*4;
            bottomP = topP+playerWidth/3*2;
        }
        ivPlayer.setBackgroundResource(res);
        ivPlayer.layout(leftP, topP, rightP, bottomP);
        Animation animationPlayer = new TranslateAnimation( 0,-(leftP-finalPX),0,-(topP-finalPY));
        animationPlayer.setDuration(1000);
        animationPlayer.setFillAfter(true);
        
        ImageView ivGoal = (ImageView)getChildAt(2);
        if(isOK.equals("1")){
            resG = R.mipmap.goal_l4;
        }else{
            resG = R.mipmap.miss_l3;
        }
        ivGoal.setBackgroundResource(resG);
        ivGoal.clearAnimation();
        
        int leftG = width/2-goalWidth/2;
        int rightG = leftG+goalWidth;
        int topG = height/2-goalWidth/4*3;
        int bottomG = topG+goalWidth/3;
        ivGoal.layout(leftG, topG, rightG, bottomG);
        ScaleAnimation animationGoal = new ScaleAnimation(0f,1.0f,0f,1.0f, Animation.RELATIVE_TO_SELF
                ,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animationGoal.setDuration(1000);
        
        ivBall.startAnimation(animationBall);
        ivPlayer.startAnimation(animationPlayer);
        ivGoal.startAnimation(animationGoal);
        new Handler().postDelayed(new Runnable(){

            public void run() {   
                setMessage();
                restart.setVisibility(View.VISIBLE);
            }   
        }, 1000);
        
        
        
    }

    private void setMessage() {
        ImageView netPlayer = new ImageView(activity);
        addView(netPlayer,generateDefaultLayoutParams());
        TextView tvPlayerName = new TextView(activity);
        addView(tvPlayerName,generateDefaultLayoutParams());
        TextView tvEventName = new TextView(activity);
        addView(tvEventName,generateDefaultLayoutParams());
        TextView tvPlace = new TextView(activity);
        addView(tvPlace,generateDefaultLayoutParams());
        TextView tvDistance = new TextView(activity);
        addView(tvDistance,generateDefaultLayoutParams());
        TextView tvType = new TextView(activity);
        addView(tvType,generateDefaultLayoutParams());
        
        netPlayer.setBackgroundResource(R.mipmap.shoot_player);
        netPlayer.layout(width/4+60, height/3*2+15, width/4+180, height/3*2+175);
        tvPlayerName.layout(width/4+210, height/3*2, width/4+390, height/3*2+45);
        tvPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tvPlayerName.setText(entity.getPlayerName1());
        tvPlayerName.setTextColor(Color.WHITE);
        tvPlayerName.setGravity(Gravity.CENTER);
        tvEventName.layout(width/4+210, height/3*2+45, width/4+390, height/3*2+84);
        tvEventName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        tvEventName.setText(entity.getEventName());
        tvEventName.setTextColor(Color.WHITE);
        tvEventName.setGravity(Gravity.CENTER);
        tvPlace.layout(width/4+210, height/3*2+84, width/4+390, height/3*2+117);
        tvPlace.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        tvPlace.setText(activity.getResources().getString(R.string.part)+entity.getBodyPartyName());
        tvPlace.setTextColor(Color.WHITE);
        tvPlace.setGravity(Gravity.CENTER);
        tvDistance.layout(width/4+210, height/3*2+117, width/4+390, height/3*2+150);
        tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        tvDistance.setText(activity.getResources().getString(R.string.distance)+entity.getDistanceName());
        tvDistance.setTextColor(Color.WHITE);
        tvDistance.setGravity(Gravity.CENTER);
        tvType.layout(width/4+210, height/3*2+150, width/4+390, height/3*2+183);
        tvType.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        tvType.setText(activity.getResources().getString(R.string.type)+entity.getBallTypeName());
        tvType.setTextColor(Color.WHITE);
        tvType.setGravity(Gravity.CENTER);
    }

}
