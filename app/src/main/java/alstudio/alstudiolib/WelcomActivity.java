package alstudio.alstudiolib;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alstudio.alstudiochatlib.chat.ChatInputLayoutEngine;
import alstudio.alstudiochatlib.chat.ChatManager;
import alstudio.alstudiolib.common.activity.BaseActivity;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQ;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQType;
import alstudio.alstudiolib.module.xmpp.core.parser.iq.TaskCompleteNotifyParser;


public class WelcomActivity extends BaseActivity implements ChatInputLayoutEngine.InputViewShowChangeListener {

    private ListView list;
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        list = (ListView)findViewById(R.id.list);

        chats = new ArrayList<String>();
        mAdapter = new ChatListAdapter(getApplicationContext(), chats);
        list.setAdapter(mAdapter);


        String stream = "<iq id=\"0279m-1\" to=\"471369@mk/android-WIFI\" type=\"set\"><query xmlns=\"jabber:moblove:task:notify\"></query></iq>\n";
        TaskCompleteNotifyParser parser = new TaskCompleteNotifyParser();
        ALIQ iq = new ALIQ();
        iq.setFrom("");
        iq.setNameSpace("jabber:moblove:task:notify");
        iq.setType(ALIQType.SET);

        try {
            parser.parseIQPackage(iq,stream,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        parentLayout = (RelativeLayout)findViewById(R.id.parent);
        emoticonsCover = (LinearLayout)findViewById(R.id.footer_for_emoticons);


        ChatManager.getInstance().createDefault(this,(int)getResources().getDimension(
                R.dimen.keyboard_height));
        ChatManager.getInstance().attach(this,(ViewGroup)findViewById(R.id.input_container),this);
    }

    @Override
    public void initUI() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        boolean result = ChatManager.getInstance().invokeBackPressed();
        if(!result)
        super.onBackPressed();
    }


    private int keyboardHeight;
    private RelativeLayout parentLayout;
    private LinearLayout emoticonsCover;



    @Override
    public void handleIMConnectionEstablished() {
        super.handleIMConnectionEstablished();
        System.out.println("IM登陆成功");
    }
    private ArrayList<String> chats;

    @Override
    public void onShow() {
        list.setSelection(list.getCount() - 1);
        System.out.println("跳到最后");
    }

    @Override
    public void onHide() {

    }


    public class ChatListAdapter extends BaseAdapter {

        private ArrayList<String> comments;
        private Context mContext;

        public ChatListAdapter(Context context, ArrayList<String> comments) {
            this.mContext = context;
            this.comments = comments;
        }

        public View getView(final int position, View convertView, ViewGroup parent){

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.chatlist_item, null);
            }

            final String item = comments.get(position);

            TextView fans_image = (TextView) v.findViewById(R.id.item_text);
            fans_image.setText(item);

            return v;


        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
