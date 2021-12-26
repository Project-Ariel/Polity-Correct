package com.example.polity_correct;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class PropositionAlgo {
    private HashMap<Long, Integer> res;
    private Proposition proposition;
    private String userPg;


    public PropositionAlgo(Proposition p) {
        this.proposition = p;
    }

    public Proposition getProposition() {
        return this.proposition;
    }

    public HashMap<Long, Integer> finalResult() {
        return this.finalResult(false);
    }

    public HashMap<Long, Integer> finalResult(boolean orderByPG) {
//        System.out.println("call create");
        createRes();

//        System.out.println("return res");
        return res;
    }

    public void createRes() {


        res = new HashMap<>();
        res.put(0L, 0); // Against
        res.put(1L, 0); // Impossible
        res.put(2L, 0); // Pro


        //        if (orderByPG) {
        //            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //
        //            DocumentReference us = FirebaseFirestore.getInstance().collection("Users").document(userID);
        //            us.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        //                @Override
        //                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        //                    if (task.isSuccessful()) {
        //                        DocumentSnapshot document = task.getResult();
        //                        String pg = (String) document.get("key_pg");
        //                        userPg = pg;
        //                        System.out.println("userPg="+userPg);
        //                    } else {
        //
        //                    }
        //                }
        //            });
        //        }


        FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", proposition.getKey())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        System.out.println("start onComplete");
                        if (task.isSuccessful()) {
//                            System.out.println("start isSuccessful");
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                long userChoice = (long) document.get("user_choice");
                                res.put(userChoice, res.get(userChoice) + 1);

                            }

//                            System.out.println("into for " + res);
//                            System.out.println("end isSuccessful");
                        } else {
                        }
//                        System.out.println("end onComplete");

                    }
                });


    }

    public void addparas() {

        String[][] arr = {{"name pg mail userType pass s"},
                {"1	יאיר*לפיד	יש*עתיד	ylapid@KNESSET.GOV.IL	parliament	yl123456	1"},
                {"2	אורנה*ברביבאי	יש*עתיד	obarbivai@knesset.gov.il	parliament	ob123456	0"},
                {"3	מאיר*כהן	יש*עתיד	cohenmeir@KNESSET.GOV.IL	parliament	co123456	1"},
                {"4	קארין*אלהרר	יש*עתיד	kelharrar@knesset.gov.il	parliament	ke123456	0"},
                {"5	מירב*כהן	יש*עתיד	mecohen@knesset.gov.il	parliament	me123456	0"},
                {"6	יואל*רזבוזוב	יש*עתיד	yrazvozov@knesset.gov.il	parliament	yr123456	1"},
                {"7	אלעזר*שטרן	יש*עתיד	sterne@KNESSET.GOV.IL	parliament	st123456	1"},
                {"8	מיקי*לוי	יש*עתיד	mickeylevy@knesset.gov.il	parliament	mi123456	0"},
                {"9	מירב*בן*ארי	יש*עתיד	bmerav@KNESSET.GOV.IL	parliament	bm123456	0"},
                {"10	רם*בן*ברק	יש*עתיד	rbenbarak@knesset.gov.il	parliament	rb123456	1"},
                {"11	יואב*סגלוביץ'	יש*עתיד	yoavse@knesset.gov.il	parliament	yo123456	1"},
                {"12	בועז*טופורובסקי	יש*עתיד	btoporovsky@KNESSET.GOV.IL	parliament	bt123456	1"},
                {"13	עידן*רול	יש*עתיד	iroll@knesset.gov.il	parliament	ir123456	1"},
                {"14	יוראי*להב*הרצנו	יש*עתיד	ylahav@knesset.gov.il	parliament	yl123456	1"},
                {"15	ולדימיר*בליאק	יש*עתיד	vbeliak@knesset.gov.il	parliament	vb123456	1"},
                {"16	רון*כץ	יש*עתיד	rkatz@knesset.gov.il	parliament	rk123456	1"},
                {"17	נירה*שפק	יש*עתיד	nshpak@knesset.gov.il	parliament	ns123456	0"},
                {"1	נפתלי*בנט	ימינה	nbennett@knesset.gov.il	parliament	nb123456	1"},
                {"2	איילת*שקד	ימינה	ASHAKED@KNESSET.GOV.IL	parliament	AS123456	0"},
                {"3	מתן*כהנא	ימינה	mkahana@knesset.gov.il	parliament	mk123456	1"},
                {"4	עמיחי*שיקלי	ימינה	ashikly@knesset.gov.il	parliament	as123456	1"},
                {"4	ניר*אורבך	ימינה	norbach@knesset.gov.il	parliament	no123456	1"},
                {"5	אביר*קארה	ימינה	akara@knesset.gov.il	parliament	ak123456	1"},
                {"6	עידית*סילמן	ימינה	isilman@knesset.gov.il	parliament	is123456	0"},
                {"1	בני*גנץ	כחול*לבן	bgantz@knesset.gov.il	parliament	bg123456	1"},
                {"2	פנינה*תמנו	כחול*לבן	ptamano@knesset.gov.il	parliament	pt123456	0"},
                {"3	חילי*טרופר	כחול*לבן	ctropper@knesset.gov.il	parliament	ct123456	1"},
                {"4	מיכאל*ביטון	כחול*לבן	mbiton@knesset.gov.il	parliament	mb123456	0"},
                {"5	אורית*פרקש*הכהן	כחול*לבן	ofarkash@knesset.gov.il	parliament	of123456	0"},
                {"6	אלון*שוסטר	כחול*לבן	aschuster@knesset.gov.il	parliament	as123456	1"},
                {"7	איתן*גינזבורג	כחול*לבן	eginzburg@knesset.gov.il	parliament	eg123456	1"},
                {"8	יעל*רון	כחול*לבן	yaelron@knesset.gov.il	parliament	ya123456	0"},
                {"1	מירב*מיכאלי	העבודה	michaelim@KNESSET.GOV.IL	parliament	mi123456	0"},
                {"2	עמר*בר*לב	העבודה	obarlev@KNESSET.GOV.IL	parliament	ob123456	1"},
                {"3	אמילי*מואטי	העבודה	emoaty@knesset.gov.il	parliament	em123456	0"},
                {"4	גלעד*קריב	העבודה	gkariv@knesset.gov.il	parliament	gk123456	1"},
                {"5	אפרת*רייטן	העבודה	eraiten@knesset.gov.il	parliament	er123456	0"},
                {"6	רם*שפע	העבודה	rshefa@knesset.gov.il	parliament	rs123456	1"},
                {"7	אבתיסאם*מרעאנה	העבודה	amarane@knesset.gov.il	parliament	am123456	0"},
                {"1	אביגדור*ליברמן	ישראל*ביתנו	aliberman@KNESSET.GOV.IL	parliament	al123456	1"},
                {"2	עודד*פורר	ישראל*ביתנו	odedfo@knesset.gov.il	parliament	od123456	1"},
                {"3	ייבגני*סובה	ישראל*ביתנו	ysuba@knesset.gov.il	parliament	ys123456	0"},
                {"4	אלי*אבידר	ישראל*ביתנו	eavidar@knesset.gov.il	parliament	ea123456	1"},
                {"5	יוליה*מלינובסקי	ישראל*ביתנו	ymalinovsky@knesset.gov.il	parliament	ym123456	0"},
                {"6	חמד*עמאר	ישראל*ביתנו	amarh@KNESSET.GOV.IL	parliament	am123456	1"},
                {"7	אלכס*קושניר	ישראל*ביתנו	akushnir@knesset.gov.il	parliament	ak123456	1"},
                {"1	גדעון*סער	תקווה*חדשה	gsaar@KNESSET.GOV.IL	parliament	gs123456	1"},
                {"2	יפעת*שאשא*ביטון	תקווה*חדשה	yifatsh@KNESSET.GOV.IL	parliament	yi123456	0"},
                {"3	זאב*אלקין	תקווה*חדשה	zelkin@KNESSET.GOV.IL	parliament	ze123456	1"},
                {"4	יועז*הנדל	תקווה*חדשה	yhendel@knesset.gov.il	parliament	yh123456	1"},
                {"5	שרן*השכל	תקווה*חדשה	shaskel@KNESSET.GOV.IL	parliament	sh123456	0"},
                {"6	בני*בגין	תקווה*חדשה	bbeni@KNESSET.GOV.IL	parliament	bb123456	1"},
                {"1	ניצן*הורוביץ	מרצ	nhorowitz@knesset.gov.il	parliament	nh123456	1"},
                {"2	תמר*זנדברג	מרצ	tzandberg@KNESSET.GOV.IL	parliament	tz123456	0"},
                {"3	יאיר*גולן	מרצ	ygolan@knesset.gov.il	parliament	yg123456	1"},
                {"4	ג'ידא*רינאווי*זועבי	מרצ	gzoaby@knesset.gov.il	parliament	gz123456	0"},
                {"5	עיסאווי*פריג'	מרצ	efrej@knesset.gov.il	parliament	ef123456	1"},
                {"6	מוסי*רז	מרצ	mraz@knesset.gov.il	parliament	mr123456	1"},
                {"1	מנסור*עבאס	רע\"ם	mabas@knesset.gov.il	parliament	ma123456	1"},
                {"2	מאזן*גנאים	רע\"ם	maganaim@knesset.gov.il	parliament	ma123456	0"},
                {"3	וליד*טאהא	רע\"ם	hak_taha@KNESSET.GOV.IL	parliament	ha123456	1"},
                {"4	סעיד*אלחורומי	רע\"ם	saeedal@knesset.gov.il	parliament	sa123456	1"},
                {"1	בנימין*נתניהו	הליכוד	pmo.heb@it.pmo.gov.il	parliament	pm123456	1"},
                {"2	יולי*אדלשטיין	הליכוד	yedelstein@knesset.gov.il	parliament	ye123456	1"},
                {"3	ישראל*כץ	הליכוד	yiskatz@knesset.gov.il	parliament	yi123456	1"},
                {"4	מירי*רגב	הליכוד	mregev@knesset.gov.il	parliament	mr123456	0"},
                {"5	יריב*לוין	הליכוד	ylevin@knesset.gov.il	parliament	yl123456	1"},
                {"6	יואב*גלנט	הליכוד	yoavg@knesset.gov.il	parliament	yo123456	1"},
                {"7	ניר*ברקת	הליכוד	nbarkat@knesset.gov.il	parliament	nb123456	1"},
                {"8	גילה*גמליאל	הליכוד	ggamliel@knesset.gov.il	parliament	gg123456	0"},
                {"9	אבי*דיכטר	הליכוד	davraham@knesset.gov.il	parliament	da123456	1"},
                {"10	גלית*דיסטל*אטבריאן	הליכוד	gd@knesset.gov.il	parliament	gd123456	0"},
                {"11	חיים*כץ	הליכוד	hkatz@knesset.gov.il	parliament	hk123456	1"},
                {"12	אלי*כהן	הליכוד	elic@knesset.gov.il	parliament	el123456	1"},
                {"13	צחי*הנגבי	הליכוד	tzhanegbi@knesset.gov.il	parliament	tz123456	1"},
                {"14	אופיר*אקוניס	הליכוד	oakunis@knesset.gov.il	parliament	oa123456	1"},
                {"15	יובל*שטייניץ	הליכוד	ysteinitz@knesset.gov.il	parliament	ys123456	1"},
                {"16	דוד*אמסלם	הליכוד	davidam@knesset.gov.il	parliament	da123456	1"},
                {"17	גדי*יברקן	הליכוד	dyaberkan@knesset.gov.il	parliament	dy123456	1"},
                {"18	אמיר*אוחנה		amiro@KNESSET.GOV.IL	parliament	am123456	1"},
                {"19	אופיר*כץ	הליכוד	ocatz@knesset.gov.il	parliament	oc123456	1"},
                {"20	חווה*עטיה	הליכוד	hatia@knesset.gov.il	parliament	ha123456	0"},
                {"21	יואב*קיש	הליכוד	yoavk@knesset.gov.il	parliament	yo123456	1"},
                {"22	דוד*ביטן	הליכוד	dbitan@knesset.gov.il	parliament	db123456	1"},
                {"23	קרן*ברק	הליכוד	kbarak@knesset.gov.il	parliament	kb123456	0"},
                {"24	שלמה*קרעי	הליכוד	shlomok@knesset.gov.il	parliament	sh123456	1"},
                {"25	מיקי*זוהר	הליכוד	mzohar@knesset.gov.il	parliament	mz123456	0"},
                {"26	אורלי*לוי*אבקסיס	הליכוד	olevy@knesset.gov.il	parliament	ol123456	0"},
                {"27	קטי*שטרית	הליכוד	kshitrit@knesset.gov.il	parliament	ks123456	0"},
                {"28	אופיר*סופר	הליכוד	osofer@knesset.gov.il	parliament	os123456	1"},
                {"29	פטין*מולא	הליכוד	pmula@knesset.gov.il	parliament	pm123456	1"},
                {"30	מאי*גולן	הליכוד	mgolan@knesset.gov.il	parliament	mg123456	0"},
                {"1	אריה*דרעי	ש\"ס	ad@knesset.gov.il	parliament	ad123456	1"},
                {"2	יעקב*מרגי	ש\"ס	ymargi@KNESSET.GOV.IL	parliament	ym123456	1"},
                {"3	יואב*בן*צור	ש\"ס	ybentzur@KNESSET.GOV.IL	parliament	yb123456	1"},
                {"4	מיכאל*מלכיאלי	ש\"ס	malkielim@knesset.gov.il	parliament	ma123456	1"},
                {"5	חיים*ביטון	ש\"ס	chbiton@knesset.gov.il	parliament	ch123456	1"},
                {"6	משה*ארבל	ש\"ס	marbel@knesset.gov.il	parliament	ma123456	1"},
                {"7	ינון*אזולאי	ש\"ס	yazulai@knesset.gov.il	parliament	ya123456	1"},
                {"8	משה*אבוטבול	ש\"ס	mabutbul@knesset.gov.il	parliament	ma123456	1"},
                {"9	אוריאל*בוסו	ש\"ס	urielb@knesset.gov.il	parliament	ur123456	1"},
                {"1	משה*גפני	יהדות*התורה	mgafni@KNESSET.GOV.IL	parliament	mg123456	1"},
                {"2	יעקב*ליצמן	יהדות*התורה	yl@knesset.gov.il	parliament	yl123456	1"},
                {"3	אורי*מקלב	יהדות*התורה	om@knesset.gov.il	parliament	om123456	1"},
                {"4	מאיר*פרוש	יהדות*התורה	mporush@KNESSET.GOV.IL	parliament	mp123456	1"},
                {"5	יעקב*אשר	יהדות*התורה	yakova@knesset.gov.il	parliament	ya123456	1"},
                {"6	ישראל*אייכלר	יהדות*התורה	ieichler@KNESSET.GOV.IL	parliament	ie123456	1"},
                {"7	יצחק*פינדרוס	יהדות*התורה	ypindrus@knesset.gov.il	parliament	yp123456	1"},
                {"1	איימן*עודה	הרשימה*המשותפת	aymanod@knesset.gov.il	parliament	ay123456	1"},
                {"2	אחמד*טיבי	הרשימה*המשותפת	atibi@KNESSET.GOV.IL	parliament	at123456	1"},
                {"3	סמי*אבו*שחאדה	הרשימה*המשותפת	sabuschada@knesset.gov.il	parliament	sa123456	1"},
                {"4	עאידה*תומא*סלימאן	הרשימה*המשותפת	aidat@KNESSET.GOV.IL	parliament	ai123456	0"},
                {"5	אוסאמה*סעדי	הרשימה*המשותפת	osamas@KNESSET.GOV.IL	parliament	os123456	1"},
                {"6	עופר*כסיף	הרשימה*המשותפת	ocassif@knesset.gov.il	parliament	oc123456	1"},
                {"1	בצלאל*סמוטריץ'	הציונות*הדתית	bezalels@KNESSET.GOV.IL	parliament	be123456	1"},
                {"2	מיכל*וולדיגר	הציונות*הדתית	michalwoldiger@knesset.gov.il	parliament	mi123456	0"},
                {"3	איתמר*בן*גביר	הציונות*הדתית	ibengvir@knesset.gov.il	parliament	ib123456	1"},
                {"4	שמחה*רוטמן	הציונות*הדתית	srothman@knesset.gov.il	parliament	sr123456	0"},
                {"5	אורית*סטרוק	הציונות*הדתית	ostruk@KNESSET.GOV.IL	parliament	os123456	0"},
                {"6	אביגדור*מעוז	הציונות*הדתית	amaaoz@knesset.gov.il	parliament	am123456	1"}};


        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i][0].split("\t");

            String userName = arr[i][1].replace("*", " ");
            String password = arr[i][5];
            String mail = arr[i][3];
            int yearOfBirth = -1;
            int gander = Integer.parseInt(arr[i][6]);
            UserType userType = UserType.parliament;
            String pg = arr[i][2].replace("*", " ");

            User u = new User(userName, password, mail, yearOfBirth, gander, userType, pg);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(mail, password);
            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        String u_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseFirestore.getInstance().collection("Users").document(u_id).set(u);
                    } else {

                    }
                }
            });


        }


    }
}