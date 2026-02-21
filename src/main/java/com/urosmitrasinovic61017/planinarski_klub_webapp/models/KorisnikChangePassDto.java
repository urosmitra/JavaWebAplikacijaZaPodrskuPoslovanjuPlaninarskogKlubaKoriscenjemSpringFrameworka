package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.NewPasswordMatches;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.OldPasswordVerify;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@OldPasswordVerify
@NewPasswordMatches
public class KorisnikChangePassDto {


    private String oldPassword; //oldPassword cemo uzeti od usera iz baze i automatski ga popuniti

    @NotBlank(message = "Password ne može biti prazno polje")
    @Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    @Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    private String oldPasswordInput; //password koji korisnik ukuca kao njegov stari password

    @NotBlank(message = "Password ne može biti prazno polje")
    @Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    @Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    private String newPassword;

    @NotBlank(message = "Password ne može biti prazno polje")
    @Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    @Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    private String newPasswordMatch;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPasswordInput() {
        return oldPasswordInput;
    }

    public void setOldPasswordInput(String oldPasswordInput) {
        this.oldPasswordInput = oldPasswordInput;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordMatch() {
        return newPasswordMatch;
    }

    public void setNewPasswordMatch(String newPasswordMatch) {
        this.newPasswordMatch = newPasswordMatch;
    }
}
