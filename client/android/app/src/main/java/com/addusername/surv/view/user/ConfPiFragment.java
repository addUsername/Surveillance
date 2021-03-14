package com.addusername.surv.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.addusername.surv.R;
import com.addusername.surv.dtos.PiSettingsDTO;
import com.addusername.surv.interfaces.ConfPiOps;
import com.addusername.surv.interfaces.MenuListener;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;

import java.util.HashMap;
import java.util.List;

public class ConfPiFragment extends Fragment implements MenuListener, ConfPiOps {

    private final String[] videoExt = {"MJPEG","H264"};
    private final String[] videoRes = {"LOW","MEDIUM","HIGH","VERY_HIGH"};
    private Spinner ids;
    private List<String> list_ids;
    private HashMap<Integer, EditText> texts;
    private HashMap<Integer, TextView> nonEditableTexts;
    private CheckBox save;
    private HashMap<Integer, Spinner> enums;
    private MenuListener ml;

    public ConfPiFragment(List<String> list_ids) {
        this.list_ids = list_ids;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conf_pi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ml = (UserActivity) getActivity();
        ids = view.findViewById(R.id.config_ids);
        view.findViewById(R.id.config_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PiSettingsDTO updateSettings = new PiSettingsDTO();
                updateSettings.setId(Long.parseLong(ids.getSelectedItem().toString()));
                updateSettings.setVideoExt(enums.get(R.id.config_extension).getSelectedItem().toString());
                updateSettings.setVideoRes(enums.get(R.id.config_res).getSelectedItem().toString());
                updateSettings.setSaveVideo(save.isChecked());

                updateSettings.setBitrate((texts.get(R.id.config_bitrate).getText().toString()));
                updateSettings.setRotation((texts.get(R.id.config_rotate).getText().toString()));
                updateSettings.setTimeRecording((texts.get(R.id.config_time).getText().toString()));
                updateSettings.setFramerate((texts.get(R.id.config_fps).getText().toString()));

                updateSettings.setModel(null);
                updateSettings.setWeights(null);
                updateSettings.setClasses(null);
                updateSettings.setThreshold((texts.get(R.id.config_threshold).getText().toString()));
                ((ViewFragmentOpsUser) getActivity()).submitSettings(updateSettings);


            }
        });

        ArrayAdapter aa = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item,list_ids.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ids.setAdapter(aa);
        ids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner",""+parent.getItemAtPosition(position).toString());
                ((ViewFragmentOpsUser) getActivity()).getPiSettings( Integer.parseInt(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setEditComponents(view);
    }

    private void setEditComponents(View v) {
        texts = new HashMap<>(8);
        enums = new HashMap<>(2);
        nonEditableTexts = new HashMap<>(4);

        save = v.findViewById(R.id.config_check_save);
        enums.put(R.id.config_extension,v.findViewById(R.id.config_extension));
        enums.put(R.id.config_res,v.findViewById(R.id.config_res));

        texts.put(R.id.config_rotate,v.findViewById(R.id.config_rotate));
        texts.put(R.id.config_time,v.findViewById(R.id.config_time));
        texts.put(R.id.config_fps,v.findViewById(R.id.config_fps));
        texts.put(R.id.config_bitrate,v.findViewById(R.id.config_bitrate));
        nonEditableTexts.put(R.id.config_classes,v.findViewById(R.id.config_classes));
        nonEditableTexts.put(R.id.config_model,v.findViewById(R.id.config_model));
        nonEditableTexts.put(R.id.config_weights,v.findViewById(R.id.config_weights));
        texts.put(R.id.config_threshold,v.findViewById(R.id.config_threshold));
    }

    @Override
    public void fill(PiSettingsDTO piSetting) {
        save.setChecked(piSetting.getSaveVideo());
        fillEnums(piSetting.getVideoExt(), piSetting.getVideoRes());

        texts.get(R.id.config_rotate).setText(piSetting.getRotation());
        texts.get(R.id.config_time).setText(piSetting.getTimeRecording());
        texts.get(R.id.config_fps).setText(piSetting.getFramerate());
        texts.get(R.id.config_bitrate).setText(piSetting.getBitrate());
        nonEditableTexts.get(R.id.config_classes).setText(piSetting.getClasses().get(0));
        nonEditableTexts.get(R.id.config_model).setText(piSetting.getModel());
        nonEditableTexts.get(R.id.config_weights).setText(piSetting.getWeights());
        texts.get(R.id.config_threshold).setText(piSetting.getThreshold());
    }

    private void fillEnums(String videoExt, String videoRes) {
        ArrayAdapter aa = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,this.videoExt);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enums.get(R.id.config_extension).setAdapter(aa);
        enums.get(R.id.config_extension).setSelection(aa.getPosition(videoExt));

        ArrayAdapter a = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,this.videoRes);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enums.get(R.id.config_res).setAdapter(a);
        enums.get(R.id.config_extension).setSelection(a.getPosition(videoRes));
    }
    @Override
    public void menuClick(MenuItem menuItem) { ml.menuClick(menuItem); }

}