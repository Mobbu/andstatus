/**
 * Copyright (C) 2015 yvolk (Yuri Volkov), http://yurivolkov.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.andstatus.app.user;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.andstatus.app.R;
import org.andstatus.app.context.MyContextHolder;
import org.andstatus.app.context.MyPreferences;
import org.andstatus.app.context.UserInTimeline;
import org.andstatus.app.util.MyUrlSpan;
import org.andstatus.app.widget.MyBaseAdapter;

import java.util.List;

class UserListViewAdapter extends MyBaseAdapter {
    private final int listItemLayoutId;
    private final List<UserListViewItem> oUsers;
    private final boolean showAvatars;
    private final boolean showWebFingerId =
            MyPreferences.userInTimeline().equals(UserInTimeline.WEBFINGER_ID);
    private final UserListContextMenu contextMenu;

    public UserListViewAdapter(UserListContextMenu contextMenu, int listItemLayoutId, List<UserListViewItem> oUsers) {
        this.listItemLayoutId = listItemLayoutId;
        this.oUsers = oUsers;
        showAvatars = MyPreferences.showAvatars();
        this.contextMenu = contextMenu;
    }

    @Override
    public int getCount() {
        return oUsers.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < getCount()) {
            return oUsers.get(position);
        }
        return UserListViewItem.getEmpty("");
    }

    @Override
    public long getItemId(int position) {
        return oUsers.get(position).getUserId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null ? newView() : convertView;
        view.setOnCreateContextMenuListener(contextMenu);
        setPosition(view, position);
        UserListViewItem item = oUsers.get(position);
        MyUrlSpan.showText(view, R.id.username,
                (showWebFingerId && !TextUtils.isEmpty(item.mbUser.getWebFingerId()) ?
                        item.mbUser.getWebFingerId() : item.mbUser.getUserName())
                + " (" + (TextUtils.isEmpty(item.mbUser.realName) ? " ? " : item.mbUser.realName) + ")",
                false);
        if (showAvatars) {
            showAvatar(item, view);
        }
        MyUrlSpan.showText(view, R.id.homepage, item.mbUser.getHomepage(), true);
        MyUrlSpan.showText(view, R.id.description, item.mbUser.getDescription(), false);
        MyUrlSpan.showText(view, R.id.profile_url, item.mbUser.getProfileUrl().toString(), true);
        showMyFollowers(view, item);
        return view;
    }

    private View newView() {
        return LayoutInflater.from(contextMenu.getActivity()).inflate(listItemLayoutId, null);
    }

    private void showAvatar(UserListViewItem item, View view) {
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar_image);
        avatar.setImageDrawable(item.getAvatar());
    }

    private void showMyFollowers(View view, UserListViewItem item) {
        StringBuilder builder = new StringBuilder();
        if (!item.myFollowers.isEmpty()) {
            int count = 0;
            builder.append(contextMenu.getActivity().getText(R.string.followed_by));
            for (long userId : item.myFollowers) {
                if (count == 0) {
                    builder.append(" ");
                } else {
                    builder.append(", ");
                }
                builder.append(MyContextHolder.get().persistentAccounts().fromUserId(userId).getAccountName());
            }
        }
        MyUrlSpan.showText(view, R.id.followed_by, builder.toString(), false);
    }
}
