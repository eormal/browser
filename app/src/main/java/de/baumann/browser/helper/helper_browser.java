/*
    This file is part of the Browser WebApp.

    Browser WebApp is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Browser WebApp is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the Browser webview app.

    If not, see <http://www.gnu.org/licenses/>.
 */

package de.baumann.browser.helper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.baumann.browser.R;
import de.baumann.browser.popups.Popup_bookmarks;
import de.baumann.browser.popups.Popup_readLater;

public class helper_browser {


    public static void prepareMenu(Activity activity, Menu menu) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        PreferenceManager.setDefaultValues(activity, R.xml.user_settings, false);
        PreferenceManager.setDefaultValues(activity, R.xml.user_settings_search, false);

        MenuItem saveBookmark = menu.findItem(R.id.action_save_bookmark);
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem search_go = menu.findItem(R.id.action_search_go);
        MenuItem search_onSite_go = menu.findItem(R.id.action_search_onSite_go);
        MenuItem search_chooseWebsite = menu.findItem(R.id.action_search_chooseWebsite);
        MenuItem history = menu.findItem(R.id.action_history);
        MenuItem save = menu.findItem(R.id.action_save);
        MenuItem share = menu.findItem(R.id.action_share);
        MenuItem search_onSite = menu.findItem(R.id.action_search_onSite);
        MenuItem downloads = menu.findItem(R.id.action_downloads);
        MenuItem settings = menu.findItem(R.id.action_settings);
        MenuItem prev = menu.findItem(R.id.action_prev);
        MenuItem next = menu.findItem(R.id.action_next);
        MenuItem cancel = menu.findItem(R.id.action_cancel);
        MenuItem pass = menu.findItem(R.id.action_pass);
        MenuItem help = menu.findItem(R.id.action_help);
        MenuItem toggle = menu.findItem(R.id.action_toggle);

        if (sharedPref.getInt("keyboard", 0) == 0) { //could be button state or..?
            saveBookmark.setVisible(false);
            search.setVisible(true);
            search_onSite_go.setVisible(false);
            search_chooseWebsite.setVisible(false);
            history.setVisible(true);
            save.setVisible(true);
            share.setVisible(true);
            search_onSite.setVisible(true);
            downloads.setVisible(true);
            settings.setVisible(false);
            prev.setVisible(false);
            next.setVisible(false);
            cancel.setVisible(false);
            pass.setVisible(true);
            help.setVisible(false);
            toggle.setVisible(true);
            search_go.setVisible(false);
            if (sharedPref.getBoolean ("help_menuShow", true)){
                help.setVisible(true); // here pass the index of save menu item
            }
        } else if (sharedPref.getInt("keyboard", 0) == 1) {
            saveBookmark.setVisible(false);
            search.setVisible(false);
            search_onSite_go.setVisible(true);
            search_chooseWebsite.setVisible(false);
            history.setVisible(false);
            save.setVisible(false);
            share.setVisible(false);
            search_onSite.setVisible(false);
            downloads.setVisible(false);
            settings.setVisible(false);
            prev.setVisible(true);
            next.setVisible(true);
            cancel.setVisible(true);
            pass.setVisible(false);
            help.setVisible(false);
            toggle.setVisible(false);
            search_go.setVisible(false);
        } else if (sharedPref.getInt("keyboard", 0) == 2) {
            saveBookmark.setVisible(true);
            search.setVisible(false);
            search_onSite_go.setVisible(false);
            search_chooseWebsite.setVisible(false);
            history.setVisible(false);
            save.setVisible(false);
            share.setVisible(false);
            search_onSite.setVisible(false);
            downloads.setVisible(false);
            settings.setVisible(false);
            prev.setVisible(false);
            next.setVisible(false);
            cancel.setVisible(true);
            pass.setVisible(false);
            help.setVisible(false);
            toggle.setVisible(false);
            search_go.setVisible(false);
        } else if (sharedPref.getInt("keyboard", 0) == 3) {
            saveBookmark.setVisible(false);
            search.setVisible(false);
            search_onSite_go.setVisible(false);
            search_chooseWebsite.setVisible(true);
            history.setVisible(false);
            save.setVisible(false);
            share.setVisible(false);
            search_onSite.setVisible(false);
            downloads.setVisible(false);
            settings.setVisible(false);
            prev.setVisible(false);
            next.setVisible(false);
            cancel.setVisible(true);
            pass.setVisible(false);
            help.setVisible(false);
            toggle.setVisible(false);
            search_go.setVisible(true);
        }
    }

    public static void setNavArrows(Activity activity, final WebView webview, ImageButton img_left, ImageButton img_right) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);

        if (sharedPref.getString ("nav", "2").equals("2") || sharedPref.getString ("nav", "2").equals("3")){
            if (webview.canGoBack()) {
                img_left.setVisibility(View.VISIBLE);
            } else {
                img_left.setVisibility(View.INVISIBLE);
            }
            img_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webview.goBack();
                }
            });

            if (webview.canGoForward()) {
                img_right.setVisibility(View.VISIBLE);
            } else {
                img_right.setVisibility(View.INVISIBLE);
            }
            img_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webview.goForward();
                }
            });
        }
    }

    public static void toolbar (final Activity activity, final  Class to, final WebView webview, Toolbar toolbar) {

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);

        toolbar.setOnTouchListener(new class_OnSwipeTouchListener_editText(activity) {
            public void onSwipeTop() {
                helper_webView.closeWebView(activity, webview);
                helper_main.closeApp(activity, to, webview);
                activity.finishAffinity();
            }
            public void onSwipeRight() {
                helper_main.switchToActivity(activity, Popup_readLater.class, "", false);
            }
            public void onSwipeLeft() {
                helper_main.switchToActivity(activity, Popup_bookmarks.class, "", false);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.edit().putString("openURL", "").apply();
                Intent intent = new Intent(activity, to);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivity(intent);
            }
        });
        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                helper_webView.closeWebView(activity, webview);
                helper_main.closeApp(activity, to, webview);
                activity.finishAffinity();
                return true;
            }
        });
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yy-MM-dd_HH-mm", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}
