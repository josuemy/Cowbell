/*
* Copyright 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ucsb.cowbell.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ucsb.cowbell.R;

/**
 * Based on the Launcher Activity for the Direct Boot sample app.
 */
public class MainActivity extends AppCompatActivity {

    /** Sets up View for the launcher by calling SchedulerFragment
     * @param savedInstanceState savedInstanceState
     */
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SchedulerFragment.newInstance())
                    .commit();
        }
    }
    @Override
    public void onBackPressed(){
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gameIntent);
    }



}
