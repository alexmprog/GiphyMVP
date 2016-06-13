package com.instinctools.phone.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.instinctools.common.di.component.ActivityComponent;

public abstract class BaseActivity<T extends ActivityComponent> extends AppCompatActivity {

    private T activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public T getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = createActivityComponent();
        }
        return activityComponent;
    }

    protected abstract T createActivityComponent();
}
