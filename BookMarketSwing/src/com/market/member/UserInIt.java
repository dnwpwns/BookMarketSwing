package com.market.member;

public class UserInIt {
	private static User mUser;

	public static void setmUser(User mUser) {
		UserInIt.mUser = mUser;
	}

	public static User getmUser() {
		return mUser;
	}
}
