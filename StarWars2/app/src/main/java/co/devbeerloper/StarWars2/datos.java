package co.devbeerloper.StarWars2;

public class datos {
    private String name;
    private String height_op;
    private String mass_diam;
    private String hair_clim;
    private String skin_grav;
    private String eye_terra;
    private String birth_surf;
    private String gender_popu;

    public datos(String name, String height_op, String mass_diam, String hair_clim, String skin_grav, String eye_terra, String birth_surf, String gender_popu) {
        this.name = name;
        this.height_op = height_op;
        this.mass_diam = mass_diam;
        this.hair_clim = hair_clim;
        this.skin_grav = skin_grav;
        this.eye_terra = eye_terra;
        this.birth_surf = birth_surf;
        this.gender_popu = gender_popu;
    }

    public String getName() {
        return name;
    }

    public String getHeight_op() {
        return height_op;
    }

    public String getMass_diam() {
        return mass_diam;
    }

    public String getHair_clim() {
        return hair_clim;
    }

    public String getSkin_grav() {
        return skin_grav;
    }

    public String getEye_terra() {
        return eye_terra;
    }

    public String getBirth_surf() {
        return birth_surf;
    }

    public String getGender_popu() {
        return gender_popu;
    }
}
