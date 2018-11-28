package com.co.ametech.heroicapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.co.ametech.heroicapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Alan M.E
 */
public class RutaSQLiteOpenHelper extends SQLiteOpenHelper implements Table_Bus, Table_Estacion, Table_Planear, Table_Ruta, Table_TipoBus, Table_Usuario {

    private static final String DATABASE_NAME = "Rutas_de_Cartagena.db";
    private static final int DATABASE_VERSION = 1;

    public RutaSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableBus());
        db.execSQL(createTableEstacion());
        db.execSQL(createTablePlanear());
        db.execSQL(createTableRuta());
        db.execSQL(createTableTipoBus());
        db.execSQL(createTableUsuario());
        llenarTablas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public String createTableBus() {

        String sql = "CREATE TABLE BUS ("
                               + Table_Bus.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                               + Table_Bus.NOMBRE + " TEXT NOT NULL,"
                               + Table_Bus.ID_TIPO + " INTEGER NOT NULL"
                               + ");";
        return sql;
    }

    @Override
    public String createTableEstacion() {

        String sql = "CREATE TABLE ESTACION ("
                               + Table_Estacion.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                               + Table_Estacion.NOMBRE + " TEXT NOT NULL,"
                               + Table_Estacion.PUNTO + " TEXT NOT NULL,"
                               + Table_Estacion.ID_BUS + " INTEGER NOT NULL"
                               + ");";
        return sql;
    }

    @Override
    public String createTablePlanear() {
        String sql = "CREATE TABLE PLANEAR ("
                               + Table_Planear.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                               + Table_Planear.NOMBRE + " TEXT NOT NULL,"
                               + Table_Planear.ORIGEN + " TEXT NOT NULL,"
                               + Table_Planear.DESTINO + " TEXT NOT NULL,"
                               + Table_Planear.ID_Ruta + " INTEGER NOT NULL"
                               + ");";
        return sql;
    }

    @Override
    public String createTableRuta() {
        String sql = "CREATE TABLE RUTA ("
                               + Table_Ruta.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                               + Table_Ruta.ID_Bus + " INTEGER NOT NULL,"
                               + Table_Ruta.PUNTOS + " TEXT NOT NULL"
                               + ");";
        return sql;
    }

    @Override
    public String createTableTipoBus() {
        String sql = "CREATE TABLE TipoBus ("
                + Table_TipoBus.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Table_TipoBus.NOMBRE + " INTEGER NOT NULL"
                + ");";
        return sql;
    }

    @Override
    public String createTableUsuario() {
        String sql = "CREATE TABLE USUARIO ("
                + Table_Usuario.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Table_Usuario.NOMBRE + " TEXT NOT NULL,"
                + Table_Usuario.EMAIL + " TEXT NOT NULL,"
                + Table_Usuario.CONTRASEÑA + " TEXT NOT NULL"
                + ");";
        return sql;
    }

    private void llenarTablas(SQLiteDatabase bd) {
        bd.execSQL("INSERT INTO TipoBus values (1, 'Corriente')");
        bd.execSQL("INSERT INTO TipoBus values (2, 'TransCaribe')");

        bd.execSQL("INSERT INTO Bus values (11, 'Campestre - IDA', 1)");
        bd.execSQL("INSERT INTO Ruta values (11, 11, 'm`i~@xupkMuBnBiAtA{@`A{AhBwAvAaChByA|AsAnA{F~EoE`EeCpBiAfAQJuBrB{BfC}@nAi@h@}AnCmB|Ci@~@Qd@a@r@_@^y@vAeA`BYj@?JBF~@j@jCvBvDjC|BrArB`Af@\\rEbCpCvAfAp@XHDFMzCIrDSzEk@pRSxBKp@O|Ak@dQChEDpCMrBC~@ChIGlED\\BJ?L[nDUjAiAfDGZBHtBv@J?`AqAjAuAVaA@SDKLAz@R|Af@x@T~Et@|Af@`@Jz@ZXFtAT@H?f@BDLFCTW~Ag@rCQx@{@~Ei@dCBH\\PCLyDvFm@t@}@bBU?eFmBkAg@oCcAcAUmG{BIDMTe@tAOTMNk@RULURWf@IX_AhCQ|@oD`M[`AIHUB_B@qALeAVy@^qA~@eGjFg@n@k@fAYr@Ol@Kx@Cz@?NLtA?XEFsEZi@MwC_@o@M_N}AwBUk@CuCVuGx@o@Bi@IYO[Y_@a@o@cA_AeA_EoEaAaAi@e@{@m@eAi@aAk@mAq@e@N]j@uC|FeBxCaCzEYPuDxCi@r@a@|@cA~Cu@lAq@p@a@`Ac@tBGx@[hC{@`CaAzBUn@uAvCoCnFmBrE{AvCkAfCU`@k@CYJUPQZQh@]rAWx@kCbGu@|A_BnCoAfCi@pAk@`AcArBe@XIJGPGr@IVW^Wj@q@nAUVU^_BbDsAlBoAvBqEfJuCdDyBjBuA~@c@^YZq@nAw@b@{@t@uCpBuBnAyAt@{BzA}@`AaAjB]d@}AbDWr@Uv@m@lCGv@EhF_@vB_@dAeAhByFdHWNc@JKLGZ?f@CVsCrCmAfBgF~G_@v@O`AC`@A^Dn@Pz@L^t@|ANVSp@]ZwB~@m@N]Ds@Ec@Gw@Y_@QkBoAWIYD}CbBsElBKLCh@B\\Pn@j@jA\\`@bEbDv@bAhEpGp@lATZXVdAtAlArCj@|@bAjAzAbBt@~@p@`AxArCd@t@Z\\|@l@p@f@p@Z\\Hp@Fj@A~@OnG}AhD}@`GkBtA_@bFaBxA_@hDmAvHeCh@Qv@Mt@IxABbAD`Fj@dGb@pDPdHb@|AZv@\\nDlCVVF@RGb@_@dBkBzBrBlFdEPRjHvF`EdDzAtA`IlIhApA~AnBzCxEPNRE~CkBxByAzBsADECQMY_A{AgA{ACK@GjA{@`@_@NSCIuB{B?MnCgFf@w@`@_AtAqCl@cAvGmMvFiKnAiCFAFB|C~AH@FCLQ~AiDBQAIGOMKmAo@gAq@')");

        bd.execSQL("INSERT INTO Bus values (12, 'Campestre - VUELTA', 1)");
        bd.execSQL("INSERT INTO Ruta values (12, 12, 'gtl~@hhblM[d@iGpLaDtGoF|JaEdIwEnIFXhBnBDZkA~@_@\\KPvA`Cl@v@JPDTSTa@XaAn@a@P_CbBgBdASCKOaCwD{@kAgDoDe@k@uFaG_DmCwCaCgHsFcJuHSBMH_CbCUKqDsCgAa@aB]_BMuIe@wF_@cGo@w@EiBGqAMw@KU?iIvCaCjAyAv@iCdAcDdAwC|@iBd@mAJc@FeFfBgCn@a@Fk@Fy@@[Is@]kBmAw@y@q@qAsAcCe@q@kE_FWa@eBsDoB_DsFcIy@cA{E}D]c@[aA]iDc@wAaBuDw@}AsAcDe@aAyAyDe@yAUg@yAoCsBcE_@k@_FgKBILM|A{@~@o@nE}DhAq@n@g@jB_Az@k@JHLR|@hCp@xAf@l@`AxA|@jAPPDJh@f@\\Pb@RfB|@rB|@lIvBlEvBfAd@nCxA`@Vl@Vb@TzAn@VHT@PCJGHOFCDAH@LFXZhBnAfBx@`Bz@|@b@HHZPJ@RAZUp@y@j@mAn@sCN_AFo@LuGZyH\\aGLwDJmBFa@n@kApB{FNU~@u@d@i@dCaDrAmB`@w@^g@j@wARYlAqArD{C`AcApAyBz@kBn@mA|AcC`B_D~B_EvCqFbA{Bv@oBvAaDv@aBrA_CvAmCfAmBfBqDvBiDpCiGzCeGlBeE~AeDzAgDdBeETu@Ps@Ls@Bs@?aAVc@f@eBNYt@{@v@oAZsAr@_Bf@aApEuDPU`LaTPFpC`BnAn@l@b@TTbGpG`AlA^l@`@d@^`@VNXN^H^?\\CzCa@xAMjC[pDi@nEa@\\Eh@OZEvDQvB[r@E|Dg@j@@\\NPBH?LIBI@W_AyBQq@e@qFi@gFIcBAuACWEU]kAIa@sAoOc@sFIs@EmABiCEs@COc@s@Ui@GWWuDEuB_@kC_@qBaB{FUo@iA}D}@sC[y@Qs@oBwFs@cCCm@BWRqBLu@~MwXfGwLxG{KJU`E_H`DyF|@sApAyBZo@jAiBtDuGtH}Ll@_AfAqAt@u@\\e@vBsB|BoBhBuAfEyDzCkC~CqCbCsBpBiBbBuA|BkCdA_BzA_BbA{@')");


        bd.execSQL("INSERT INTO Bus values (2, 'Vehitrans - IDA', 1)");
        bd.execSQL("INSERT INTO Ruta values (2, 2, 'm~j~@luqkMz@V`Ch@^NZRnBzA|D`CBDCLiAfAoBtBmAzAiA~AiIfN}BrDgCvEeFdIyDzGyBtDoAjBsBrDiDjG}@pBUXg@`AmApCkA~BGRYXMRyA|CW\\KJQHeBl@u@HuA?}@M{@[q@c@W]QOOFCFjAzCpA`EfAlEr@pBbA`DjAfDdAhDZz@dDjLd@xALn@LjAJhD`@vBn@nGLz@h@bFb@hFt@~Hh@rGDtAFz@`@~BPrAj@dGT`DLhAV~AVfCNbATfDGJ_BLoBFi@MwC_@o@M_N}AwBUk@CuCVuGx@o@Bi@IYO[Y_@a@o@cA_AeA_EoEaAaAi@e@{@m@eAi@aAk@mAq@e@N]j@uC|FeBxCaCzEYPuDxCi@r@a@|@cA~Cu@lAq@p@a@`Ac@tBGx@[hC{@`CaAzBUn@uAvCoCnFmBrE{AvCkAfCU`@k@CYJUPQZQh@]rAWx@kCbGu@|A_BnCoAfCi@pAk@`AcArBe@XIJGPGr@IVW^Wj@q@nAUVU^_BbDsAlBoAvBqEfJuCdDyBjBuA~@c@^YZq@nAw@b@{@t@uCpBuBnAyAt@{BzA}@`AaAjB]d@}AbDWr@Uv@m@lCGv@EhF_@vB_@dAeAhByFdHWNc@JKLGZ?f@CVsCrCmAfBgF~G_@v@O`AC`@A^Dn@Pz@L^lAdCAJe@d@{B~@_ATg@?{AMk@Mc@Q_BeASKc@A[LmBfAqD|Ae@MMQq@mBoHwPkB}EwCgGyAoCk@aAeEuIg@{@c@oAI_@Ca@BiDAc@O}@YgA]w@_A}AaBuCqCmEuC_Fe@_AwAcCm@_A_BqCyC}Es@uAMc@ScAe@sDm@mFUoCYiFO{FIgBOkH?mBO_@_@Mu@IeH_@cJa@u@IcESmDSmBSuAEwAWg@i@c@o@_FcF_DqCsB}BmByBsAcBmD{Ds@{@QL')");

        bd.execSQL("INSERT INTO Bus values (3, 'Vehitrans - VUELTA', 1)");
        bd.execSQL("INSERT INTO Ruta values (3, 3, 'i`y~@rk{kMjJhKlAzAvC|DbAtAn@hBd@rBTx@`@r@d@h@hApAlArApE~EbBhB|BfCbCnCxAnBhC`DzBdD|B`D~AlBv@jAnAlB`AtA|A|BvBnCb@j@h@r@j@v@`@f@`@^nAr@lErBbIvLb@n@jCjEn@`Af@`AX`AJv@Bt@AvDNbAFVvBhE|B`FhAtBPTTh@rAdC`BdDv@jBnBfF|@hBpBnExAnDZfBNZPF\\Al@YpCmA|CeBFAHBnCdBVJr@P\\Ft@Bh@G`@ItEeBT@dAd@JBH@^AtFaBpBi@fCa@j@Gz@EHCHEDKHe@EmA?}@L{AL]Ts@^k@X]d@a@f@[`@[p@cA`@mA~@{DFu@HkHz@uPHuDL}AHWf@_A\\}@hAiDRa@Za@n@i@pAaB`@c@rAkBjAeBvAmCj@_A`@a@hEoD`AcApAyBz@kBn@mA|AcC`B_D~B_EvCqFbA{Bv@oBvAaDv@aBrA_CvAmCfAmBfBqDvBiDpCiGzCeGlBeE~AeDzAgDdBeETu@Ps@Ls@Bs@?aAVc@f@eBNYt@{@v@oAZsAr@_Bf@aApEuDPU`LaTPFpC`BnAn@l@b@TTbGpG`AlA^l@`@d@^`@VNXN^H^?\\CzCa@xAMjC[pDi@nEa@\\Eh@OZEvDQvB[r@E|Dg@j@@\\NZBLIBI@W_AyBQq@e@qFi@gFIcBAuACWEU]kAIa@sAoOc@sFIs@EmA@eDGg@c@s@Ui@GWWuDEuBIs@u@iEy@wCuD{Lk@cBQs@oBwFm@qBEQAY@k@RqBLu@~MwXfGwLxG{KJU`E_H`DyF|@sApAyBZo@jAiBtDuGtH}Ll@_AfAqAt@u@\\e@vBsB|BoBhBuAfEyDzCkC~CqCbCsBpBiBbBuA|BkCdA_BzA_BbA{@@KCGUEa@XWVsA|A{@jAsBdC_B`BoCzB_CxByDhDcB~A{BlB]\\}DdD_@Vk@d@S?KGmA{@uBuAkByASIiEcAc@OCG')");

        bd.execSQL("INSERT INTO Bus values (4, 'A102P - IDA', 2)");
        bd.execSQL("INSERT INTO Ruta values (4, 4, '{rh~@lnrkMeB{BgJqHs@c@_Aw@u@}@u@mAuDqFwBmDo@iAUG[F[ReC`C}ArAiFvEgH|FaEfE}AnBcId@aAB{B?gENg@J[JQLYZsBpDSX{@n@[L}Bb@mAXe@P_An@k@ZoBnA_Ap@}BvA_@Z_@r@I^[zCe@nDi@|GUxBSbAKZWd@wBzC[XcBbAwAh@{Bl@iBbAg@^c@TmBh@k@RCLBR^|@')");
        bd.execSQL("INSERT INTO Estacion values (1, 'Paradero', '10.371111,-75.466214', 4)");
        bd.execSQL("INSERT INTO Estacion values (2, 'Paradero', '10.372304,-75.465088', 4)");
        bd.execSQL("INSERT INTO Estacion values (3, 'Paradero', '10.373919,-75.4638', 4)");
        bd.execSQL("INSERT INTO Estacion values (4, 'Paradero', '10.375248,-75.462148', 4)");
        bd.execSQL("INSERT INTO Estacion values (5, 'Paradero', '10.376283,-75.460678', 4)");
        bd.execSQL("INSERT INTO Estacion values (6, 'Paradero', '10.376663,-75.46063', 4)");
        bd.execSQL("INSERT INTO Estacion values (7, 'Paradero', '10.379121,-75.462599', 4)");
        bd.execSQL("INSERT INTO Estacion values (8, 'Paradero', '10.380393,-75.463758', 4)");
        bd.execSQL("INSERT INTO Estacion values (9, 'Paradero', '10.381406,-75.464782', 4)");
        bd.execSQL("INSERT INTO Estacion values (10, 'Paradero', '10.382636,-75.465474', 4)");
        bd.execSQL("INSERT INTO Estacion values (11, 'Paradero', '10.385242,-75.46571', 4)");
        bd.execSQL("INSERT INTO Estacion values (12, 'Paradero', '10.386519,-75.46674', 4)");
        bd.execSQL("INSERT INTO Estacion values (13, 'Paradero', '10.387469,-75.467513', 4)");
        bd.execSQL("INSERT INTO Estacion values (14, 'Paradero', '10.390192,-75.469235', 4)");
        bd.execSQL("INSERT INTO Estacion values (15, 'Paradero', '10.391089,-75.471632', 4)");
        bd.execSQL("INSERT INTO Estacion values (16, 'Paradero', '10.391659,-75.474132', 4)");
        //---
        bd.execSQL("INSERT INTO Bus values (5, 'A107P IDA Blasdeleso - El Amparo', 2)");
        bd.execSQL("INSERT INTO Ruta values (5, 5, 'wdh~@ncvkMkA{@i@c@}C}CNqA}@y@uB}AgAgAyAsAc@OaGy@aGkAQAGlDe@bFq@pIYxF[hDFj@RvEVxCH~ACJIHWHWRk@l@k@|A}@b@i@v@i@n@YEyA[k@Eg@MkGcBCeAE_@EMoAkAiA}@AKC{A@cAAOIKUK{H{AeAQq@SiEoDy@o@]Q{AYiC]oFWgBEiA@YBWFoFNi@Jm@PYLMLMNwA~CiAfByArAWN{An@UAIOQu@q@qBeAqDoA}DoCaIGYAOFcAHo@T_BnAoCx@cBn@}AeADoBf@kB@uA[kAm@sAgBu@uBUsAQg@')");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.370469,-75.48295', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.372165,-75.48157', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.374321,-75.481011', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.375044,-75.483547', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.375318,-75.485167', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.37536,-75.48816', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.37646,-75.490647', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.378048,-75.490518', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.379658,-75.48907', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.380776,-75.487578', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.382755,-75.486661', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.384871,-75.485577', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.386842,-75.485457', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.38966,-75.486969', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.392678,-75.484105', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.392342,-75.481731', 5)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.395028,-75.478829', 5)");
        //
        bd.execSQL("INSERT INTO Bus values (6, 'A107P VUELTA Blasdeleso - El Amparo', 2)");
        bd.execSQL("INSERT INTO Ruta values (6, 6, 'mhm~@f~tkMx@vCxCpItAdE~@|Cf@rAt@~BdClIjEhNhA`EP`AB`@JtBLTF@FAJO?I{@yEcA}DZO`@InAq@p@g@r@o@hAyAlAsCTa@PQXKZE`CYbDQrA?tCBfBJ|BPz@Jj@Lj@@t@Jf@L`@VxAlA|BdBVJpFhAXDj@NlCb@BBAjFUfBEz@Q|@[x@s@|B@R^PfAb@JBHAzAgBt@eA^sAHAjAVzBr@t@Nf@Hh@F~Bb@RERa@n@}@PQb@SNUTs@NYb@e@fAy@BU?[]qDe@gGx@sL@{@LsB\\kE`@{D@_BDcABER?L@`A\\XFnFx@nANZHv@FXFt@x@lBdBrA`A|ArAfB~@NLLNhB|A^T\\JOp@In@CH')");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.395097,-75.478909', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.392538,-75.485084', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.391678,-75.487149', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.390702,-75.489677', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.390774,-75.488797', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.389287,-75.486806', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.387588,-75.485727', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.385119,-75.485862', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.382787,-75.486811', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.380534,-75.487755', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.38038,-75.490695', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.380074,-75.491553', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.379156,-75.490582', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.378106,-75.490534', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.376428,-75.490716', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.37531,-75.487836', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.375262,-75.485239', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.374798,-75.481935', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.371911,-75.481785', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.370476,-75.483018', 6)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.369025,-75.484129', 6)");
        //----
        bd.execSQL("INSERT INTO Bus values (7, 'T101E Variante - Centro por Avenida Pedro de Heredia', 2)");
        bd.execSQL("INSERT INTO Ruta values (7, 7, 'ekm~@nutkMvC~IbBxF|CtI^hAD^GjAOp@{C|GUn@Kn@SrCE~@EfJO~Ce@lCm@bB{@`B_AxAc@f@{DnDqBbB}AdAgBr@wBb@kFn@}Bh@sCdBw@n@oAzAwJfOaAlBg@jAk@dAcCrDeAlBoA`D_@lBy@zGe@|FY`CW`Dq@nG]lE{A`Pk@xE[rASlCMzCQzAKf@MXY|@q@rCg@tAaApBgAnCm@jAsEbIqCtGqAjCaChEaErGcAbBwBhEyApDqBhCiA~@qDjCUJ]`@g@~@uAjAaD|BaC~AsAx@s@ZsA~@y@z@kBlDo@vAe@hAi@xAUt@g@nCGj@@vCGr@u@hDg@lAc@l@wEzFeAtAcBlBq@z@}CnEwCzDc@r@k@xAK`A@n@Hl@Pl@t@xAtAlDbFvH~ChE|AbBfBtB^\\VNzAj@\\ZZh@NPhD|BbBbAZThBhCXf@b@d@xApANPf@bANNLH^@b@S')");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CASTELLANA', '10.394434,-75.486112', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('EJECUTIVOS', '10.399544,-75.493647', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('ESTADIO', '10.404005,-75.497737', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('SENA', '10.406606,-75.503205', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('MARIA AUXILIADORA', '10.408959,-75.515753', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('BAZURTO', '10.414067,-75.524473', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('POPA', '10.420453,-75.531061', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CHAMBACÚ', '10.425901,-75.540478', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('VENEZUELA', '10.425072,-75.546569', 7)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('PARQUE MARINA', '10.419981,-75.551502', 7)");

        //---
        bd.execSQL("INSERT INTO Bus values (9, 'T102P Terminal Gallo - Boca Grande', 2)");
        bd.execSQL("INSERT INTO Ruta values (9, 9, 'cjm~@rztkMxC`J`B~DlAhDb@zAXpAH|@?LKd@}@nB_B|CWfAKl@G`AK`CCbESpIY~Au@bCmAbCs@dAiAdAmJ|Hq@b@sAf@qATsAJwC^u@L_AVmB~@q@d@s@j@uAzA}@`B_CrDQVi@j@}CfFs@zAy@|AkAlB}@nAiAhBe@nA_@pAo@zD_@xCOdBGrBGbAk@hGa@|C_@dB[jCOjEe@fF]zC_@tDIh@a@pBSvAOlB?X@ZG|AKv@UpAk@fBq@jCSl@}AfDoAzCkA`CU`@_ApAU^]n@mAfC]p@eA~Ci@jAaAjBiA`B]l@mMfVkAtAyClCs@h@kDhDiHtFqBtAa@Po@ZaAt@cB|AwAzBm@jAe@dA]~@Wz@g@zBUbBMrAGnCEh@SpAg@zAi@~@m@x@kCdD{C~D{AbBuClD_FzG[p@Ql@EVEt@FdADTb@pAVf@`E~GbApBtAhBt@dAx@bAb@`@nArAtCnDRJ`AVVJZVJN^p@pA`ApE|Cd@b@xBfDl@n@jA|@NRl@bBHPFDR?\\KrBw@nA}@d@UdAa@~Bw@rEeBxDqAr@[r@Ud@KLA|BJbFXbABjCL`GTZ?tBLhBRz@RVNx@d@x@l@l@h@bBnAzExDdCxBxDvCtAnAbG~EnAhAdE~DvC`Dp@x@`BxBrBxCdAdBP?HE|@k@')");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('TERMINAL EL GALLO', '10.395268,-75.478024', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CASTELLANA', '10.394434,-75.486112', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('EJECUTIVOS', '10.399544,-75.493647', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('ESTADIO', '10.404005,-75.497737', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('SENA', '10.406606,-75.503205', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('MARIA AUXILIADORA', '10.408959,-75.515753', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('BAZURTO', '10.414067,-75.524473', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('POPA', '10.420453,-75.531061', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CHAMBACÚ', '10.425901,-75.540478', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('VENEZUELA', '10.425072,-75.546569', 9)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('PARQUE MARINA', '10.419981,-75.551502', 9)");

        bd.execSQL("INSERT INTO Bus values (1, 'X106P Variante - Centro por Av. Pedro de Heredia', 2)");
        bd.execSQL("INSERT INTO Ruta values (1, 1, '{hi~@x~pkMy@bAc@b@{EfEaC~BeEvDiB|AkBdBmB`BmA~@uChCeAdAkApA{@bA}@jAmBvCoB`DqA|BaCvD{G~K_MxSuHxMgBrDiBhD_ClFc@d@iBrDUZKH}@ZuA\\cADc@CuAU]Ou@e@s@u@ODCFlA|CtAbEn@pCDXARE\\IT{C|GUn@Kn@SrCE~@EfJO~Ce@lCm@bB{@`B_AxAc@f@{DnDqBbB}AdAgBr@wBb@kFn@}Bh@sCdBw@n@oAzAwJfOaAlBg@jAk@dAcCrDeAlBoA`D_@lBy@zGe@|FY`CW`Dq@nG]lE{A`Pk@xE[rASlCMzCQzAKf@MXY|@q@rCg@tAaApBgAnCm@jAsEbIqCtGqAjCaChEaErGcAbBwBhEyApDqBhCiA~@qDjCUJ]`@g@~@uAjAaD|BaC~AsAx@s@ZsA~@y@z@kBlDo@vAe@hAi@xAUt@g@nCGj@@vCGr@u@hDg@lAc@l@wEzFeAtAcBlBq@z@}CnEwCzDc@r@k@xAK`A@n@Hl@Pl@t@xAtAlDbFvH~ChE|AbBfBtB^\\VNzAj@\\ZZh@NPhD|BbBbAZThBhCXf@b@d@xApANPf@bANNLH^@b@S')");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CASTELLANA', '10.394434,-75.486112', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('EJECUTIVOS', '10.399544,-75.493647', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('ESTADIO', '10.404005,-75.497737', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('SENA', '10.406606,-75.503205', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('MARIA AUXILIADORA', '10.408959,-75.515753', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('BAZURTO', '10.414067,-75.524473', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('POPA', '10.420453,-75.531061', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('CHAMBACÚ', '10.425901,-75.540478', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('VENEZUELA', '10.425072,-75.546569', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('PARQUE MARINA', '10.419981,-75.551502', 1)");

        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.374693,-75.458531', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.376791,-75.460543', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.378986,-75.462541', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.381323,-75.464698', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.38283,-75.466691', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.383885,-75.468201', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.386144,-75.471516', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.388394,-75.474885', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.389831,-75.477161', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.390621,-75.478484', 1)");
        bd.execSQL("INSERT INTO Estacion(nombre, punto, idbus) values ('Paradero', '10.391573,-75.480102', 1)");
    }

}
