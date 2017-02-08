package lucas.com.footballdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FootballLineLayout footline;
    private TransformballLayout transformfoot;
    private ImageView restart;
    private ImageView reback;
    ArrayList<ShootEntity> eventList;
    ArrayList<ShootEntity> pointEvent;
    private FootballGoalLayout footgoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//需要添加的语句

        setView();
        setListener();

        getData();

    }

    private void setView() {
        restart = (ImageView)findViewById(R.id.restart);
        reback = (ImageView)findViewById(R.id.reback);
        footgoal =(FootballGoalLayout)findViewById(R.id.footgoal);
        footline = (FootballLineLayout)findViewById(R.id.footline);
        transformfoot = (TransformballLayout)findViewById(R.id.transformfoot);
        restart.setVisibility(View.VISIBLE);
    }

    private void setListener() {
        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                footgoal.removeAllViews();
                footgoal.setVisibility(View.INVISIBLE);
                footline.setData(pointEvent, MainActivity.this);
                transformfoot.setData(pointEvent,eventList, MainActivity.this);
            }
        });

        reback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {

        eventList = new ArrayList<ShootEntity>();
        pointEvent = new ArrayList<ShootEntity>();
        for (int i = 0; i < 2; i++) {
            ShootEntity entity = new ShootEntity();
            entity.setEventID(i+1);
            entity.setPlayID(""+i);
            entity.setEventName("事件"+i);
            entity.setTeamID("2");
            entity.setPlayerName1("名称"+i);
            entity.setNumberBack1(i+"");
            entity.setBodyPartyName("部位"+i);
            entity.setDistanceName("距离"+i);
            entity.setBallTypeName("种类"+i);
            entity.setIsOK(""+1);
            entity.setBallXPos(400+100*i);
            entity.setBallYPos(200+30*i);
            entity.setFrameXPos(400+100*i);
            entity.setFrameYPos(200+30*i);
            entity.setGkXPos(400+100*i);
            entity.setGkYPos(200+30*i);
            eventList.add(entity);
        }

        for (int i = 0; i < 4; i++) {
            ShootEntity entity = new ShootEntity();
            entity.setLineXpos(4000+2000*i);
            entity.setLineYpos(2000+300*i);
            entity.setLineEventID(i+1);
            entity.setTeamID(i+"");
            pointEvent.add(entity);
        }

        footline.setData(pointEvent, MainActivity.this);
        transformfoot.setData(pointEvent,eventList, MainActivity.this);

    }
}
