package com.june0216.chat.global.util;

public abstract class ConstantUtil { //상수 클래스를 추상 클래스로 선언하는 이유는 인스턴스화를 방지하기 위함(객체 생성이 필요없음)
	public static final String MASTER_DATASOURCE = "masterDataSource";
	public static final String SLAVE_DATASOURCE = "slaveDataSource";

	public static final String DEFAULT_PROFILE
		= "https://project-adopt-bucket.s3.ap-northeast-2.amazonaws.com/other/default-profile-image.jpeg";

	public static final String DEFAULT_ADOPT_IMAGE
		="https://project-adopt-bucket.s3.ap-northeast-2.amazonaws.com/other/cat.jpeg";

	public static final String KAFKA_TOPIC = "kuddy-chat";
	public static final String KAFKA_AGGREGATION = "aggregation";
}
