package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 27/02/2017.
 */

public class LanguageClass {
    private String langname,lang_name,iso_code,img;
    private int language;

    public String getLangname() {
        return langname;
    }

    public void setLangname(String langname) {
        this.langname = langname;
    }

    public int getLanguage() {
        return language;
    }

    public LanguageClass(String langname, int language,String lang_name,String iso_code,String img) {
        this.langname = langname;
        this.language = language;
        this.lang_name=lang_name;
        this.iso_code=iso_code;
        this.img=img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
