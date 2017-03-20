package com.example.app.assignment;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.assignment.object.ObjectAccount;
import com.example.app.assignment.object.ObjectData;
import com.example.app.assignment.object.ObjectFullData;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

//    @ViewById(R.id.activity_main_tv_show)
//    TextView tvShow;

    @ViewById(R.id.activity_main_et_username)
    EditText etUserName;
    @ViewById(R.id.activity_main_et_password)
    EditText etPassword;
    @ViewById(R.id.activity_main_btn_login)
    Button btnLogin;
    @ViewById(R.id.activity_main_lv_list)
    ListView lvList;

    private String strUser;
    private String strPassword;
    private QuickAdapter adapterData;

    @Bean
    protected DataService dataService;

    @AfterViews
    public void init() {


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser = etUserName.getText().toString();
                strPassword = etPassword.getText().toString();
                adapterData = new QuickAdapter<ObjectData>(MainActivity.this, R.layout.item_list_view) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, ObjectData item) {
                        helper.setText(R.id.data_item_tv_id, "Id: " + item.getId())
                                .setText(R.id.data_item_tv_creatorId, "creatorId: " + item.getCreatorId())
                                .setText(R.id.data_item_tv_started_date, "Start Date: " + item.getStartedDate())
                                .setText(R.id.data_item_tv_end_date, "End Date: " + item.getEndDate())
                                .setText (R.id.data_item_tv_content, "content: " + item.getContent());
                    }
                };
              /*  if(list != null)
                {
                    adapterData.clear();
                    adapterData.notifyDataSetChanged();
                }*/
                lvList.setAdapter(adapterData);

                login(strUser, strPassword);
            }

        });
    }

    @Background
    public void login(String strUser, String strPassword) {
        dataService.login("password", strUser, strPassword, new LoginSuccessListener() {
            @Override
            public void onSuccess(ObjectAccount account) {
                try {
                    dataService.loadData(account.getAccessToken(), new LoadDataSuccessListener() {
                        @Override
                        public void onSuccess(ObjectFullData listData) {
                            uiThread(listData);
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Password or username incorected!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @UiThread
    void uiThread(ObjectFullData objectFullData) {
        ArrayList<ObjectData> listData = objectFullData.getData();

        adapterData.addAll(listData);
    }
}