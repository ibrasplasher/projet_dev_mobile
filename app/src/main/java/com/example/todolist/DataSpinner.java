package com.example.todolist;

import java.util.ArrayList;
import java.util.List;

public class DataSpinner {
    public static List<RondSpinner> getRondSpinnerList() {

        int petitBleuId = R.drawable.petit_bleu;
        int petitVertId = R.drawable.petit_vert;
        int petitGrisId = R.drawable.petit_gris;
        int petitRougeId = R.drawable.petit_rouge;


        List<RondSpinner> rondList = new ArrayList<>();

        RondSpinner RondBleu = new RondSpinner();
        RondBleu.setName("In progress");
        RondBleu.setImageId(petitBleuId);
        RondBleu.setImage(R.drawable.petit_bleu);
        rondList.add(RondBleu);

        RondSpinner RondVert = new RondSpinner();
        RondVert.setName("Done");
        RondVert.setImageId(petitVertId);
        RondVert.setImage(R.drawable.petit_vert);
        rondList.add(RondVert);

        RondSpinner RondGris = new RondSpinner();
        RondGris.setName("Todo");
        RondGris.setImageId(petitGrisId);
        RondGris.setImage(R.drawable.petit_gris);
        rondList.add(RondGris);


        RondSpinner RondRouge = new RondSpinner();
        RondRouge.setName("Bug");
        RondRouge.setImageId(petitRougeId);
        RondRouge.setImage(R.drawable.petit_rouge);
        rondList.add(RondRouge);


        return rondList;


    }
}
