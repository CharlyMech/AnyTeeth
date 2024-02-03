package com.charlymech.anyteeth.Enums;

import java.util.ArrayList;

public enum Province {
	//? Tener en cuenta que está pensado por el momento para España -> Se puede escalar a diferentes paises
	ALAVA("Álava"), ALBACETE("Albacete"), ALICANTE("Alicante"), ALMERIA("Almería"),
	ASTURIAS("Asturias"), AVILA("Ávila"), BADAJOZ("Badajoz"), BARCELONA("Barcelona"),
	BURGOS("Burgos"), CACERES("Cáceres"), CADIZ("Cádiz"), CANTABRIA("Cantabria"),
	CASTELLON("Castellón"), CIUDAD_REAL("Ciudad Real"), CORDOBA("Córdoba"),
	CUENCA("Cuenca"), GERONA("Gerona"), GRANADA("Granada"), GUADALAJARA("Guadalajara"),
	GUIPUZCOA("Guipúzcoa"), HUELVA("Huelva"), HUESCA("Huesca"), ISLAS_BALEARES("Islas Baleares"),
	JAEN("Jaén"), LA_CORUNA("La Coruña"), LA_RIOJA("La Rioja"), LAS_PALMAS("Las Palmas"),
	LEON("León"), LERIDA("Lérida"), LUGO("Lugo"), MADRID("Madrid"),
	MALAGA("Málaga"), MURCIA("Murcia"), NAVARRA("Navarra"), ORENSE("Orense"),
	PALENCIA("Palencia"), PONTEVEDRA("Pontevedra"), SALAMANCA("Salamanca"), SANTA_CRUZ_DE_TENERIFE("Santa Cruz de Tenerife"),
	SEGOVIA("Segovia"), SEVILLA("Sevilla"), SORIA("Soria"), TARRAGONA("Tarragona"),
	TERUEL("Teruel"), TOLEDO("Toledo"), VALENCIA("Valencia"), VALLADOLID("Valladolid"),
	VIZCAYA("Vizcaya"), ZAMORA("Zamora"), ZARAGOZA("Zaragoza");

	private final String province;

	Province(String province) {
		this.province = province;
	}

	public String getProvince() {
		return this.province;
	}

	public static ArrayList<String> getProvincesNames() {
		ArrayList<String> provincesNames = new ArrayList<>();
		for(Province p : Province.values()) {
			provincesNames.add(p.getProvince());
		}
		return provincesNames;
 	}
}
