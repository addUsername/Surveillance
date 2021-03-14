package com.addusername.surv.model.user;

import com.addusername.surv.dtos.PiDTO;
import com.addusername.surv.dtos.PiSettingsDTO;
import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsModelUser;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserModel implements ModelOpsUser {

    private final PresenterOpsModelUser pomu;
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService multiExecutor = Executors.newFixedThreadPool(4);
    private final UserService us;

    public UserModel(PresenterOpsModelUser pomu, String token, String host, File filesDir) {
        this.pomu = pomu;
        us = new UserService(token,host, filesDir.getAbsolutePath());
    }

    @Override
    public void doGetHome() {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() { pomu.homeReturn(us.doHome()); }
        });
    }

    @Override
    public void doAddRpi(PiDTO piDTO) {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pomu.addRpiReturn(us.doAddRpi(piDTO));
                doDump();
            }
        });
    }
    @Override
    public void loadImgs(List<Integer> raspberryIds) {
        for(Integer id: raspberryIds){
            this.multiExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    InputStream img = us.getImg(id);
                    if(img == null) {
                        img = us.getImgMock();
                    }
                    pomu.setImg(img, id);
                }
            });
        }
    }

    @Override
    public void getFromRPi(int rpiId, String action) {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                switch (action){
                    case "stream":
                        String html = us.takeStream(rpiId);
                        if(html != null){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pomu.loadWebview(html);
                        }
                        break;
                    case "screenshot":
                        InputStream is = us.takeScreenShoot(rpiId);
                        if(is == null){
                            // show error menssage
                            return;
                        }
                        pomu.showImg(is);
                        break;
                }
            }
        });
    }

    @Override
    public void getPiSettings(int id) {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PiSettingsDTO piSettings = us.getSettings(id);
                pomu.loadPiSettings(piSettings);
            }
        });
    }

    @Override
    public void submitPiSettings(PiSettingsDTO updateSettings) {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                us.updateSettings(updateSettings);
            }
        });
    }

    private void doDump(){ us.doDump(); }
}
