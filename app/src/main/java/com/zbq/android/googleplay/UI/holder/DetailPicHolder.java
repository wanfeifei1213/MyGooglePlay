package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/11/3.
 */

public class DetailPicHolder extends BaseHolder<AppInfo> {
    private ImageView[] ivPics;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        ivPics = new ImageView[5];
        ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.screen;
        for (int i = 0; i < screen.size(); i++) {
            if (i < screen.size()) {
                mBitmapUtils.display(ivPics[i], HttpHelper.URL + "image?name=" + screen.get(i));
            } else {
                ivPics[i].setVisibility(View.GONE);
            }
        }
    }
}
