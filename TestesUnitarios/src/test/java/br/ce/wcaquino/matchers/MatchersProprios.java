package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiNumaSegundaFeira(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMacther ehHojeComDiferencaDias(Integer qtdDias){
        return new DataDiferencaDiasMacther(qtdDias);
    }

    public static DataDiferencaDiasMacther ehHoje(){
        return new DataDiferencaDiasMacther(0);
    }

}
