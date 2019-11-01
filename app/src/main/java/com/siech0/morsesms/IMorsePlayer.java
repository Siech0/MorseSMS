package com.siech0.morsesms;

import java.util.List;

public interface IMorsePlayer {
    void play(IMorseOutputStream data, int unitSize);
    String playerName();
    List<String> getRequiredPermissions();
    String getPermissionDialogTitle(String perm);
    String getPermissionDialogText(String perm);
}
