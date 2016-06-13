package com.instinctools.wear.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.instinctools.common.di.component.ActivityComponent;

public abstract class BaseActivity<T extends ActivityComponent> extends Activity {

    private T activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

