package com.example.innervoice2;

public class info {
    String songname, songlyrics, trackpath;
    boolean permission;

    public info() {

    }

    public info(String songname, String songlyrics, String trackpath) {
        this.songname = songname;
        this.songlyrics = songlyrics;
        this.trackpath = trackpath;
        this.permission = permission;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getSonglyrics() {
        return songlyrics;
    }

    public void setSonglyrics(String songlyrics) {
        this.songlyrics = songlyrics;
    }

    public String getTrackpath() {
        return trackpath;
    }

    public String setTrackpath(String trackpath) {
        this.trackpath = trackpath;
        return trackpath;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}

