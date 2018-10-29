package com.example.sweethome.rssreader.screens.add_channel.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.add_channel.presenter.AddChannelPresenter;
import com.example.sweethome.rssreader.screens.add_channel.presenter.IAddChannelPresenterContract;

public final class AddChannelActivity extends AppCompatActivity implements IAddChannelPresenterContract {

    private AddChannelPresenter mAddChannelPresenter;
    private EditText linkEditText;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        linkEditText = findViewById(R.id.add_links_EditText);
        nameEditText = findViewById(R.id.add_name_EditText);

        Button addLinkButton = findViewById(R.id.add_links_Button);

        addLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddChannelPresenter.onAddLinkButtonClick();
            }
        });

        mAddChannelPresenter = new AddChannelPresenter(this, this);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddChannelActivity.class);
    }

    //region IAddChannelPresenterContract override
    @Override
    public void addLinkSuccess() {
        Toast.makeText(this, "Канал добавлен", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addLinkFail() {
        Toast.makeText(this, "Канал уже существует или неправильный адрес канала", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getLink() {
        return linkEditText.getText().toString();
    }

    @Override
    public String getName() {
        return nameEditText.getText().toString();
    }
    //endregion


    @Override
    protected void onResume() {
        mAddChannelPresenter.attach(this,this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAddChannelPresenter.detach();
        super.onPause();
    }
}
