package com.robotemi.sdk.model

import androidx.annotation.Keep

/**
 * This class is used to store the organization information.
 * @property id The ID of the organization.
 * @property name The name of the organization.
 * @property profileImage The profile image media key of the organization. Exchange signed url to get the actual image. See [com.robotemi.sdk.Robot.getSignedUrlByMediaKey]
 * @property robotCount The count of robots in the organization.
 * @property region The region of the organization. Can be "global" or "chn".
 * @property rootAccount The root account userId of the organization. This value can be matched with [com.robotemi.sdk.UserInfo.userId]
 */
@Keep
data class OrganizationInfo(
    val id: String = "",
    val name: String = "",
    val profileImage: String = "",
    val robotCount: Int = 0,
    val region: String = "",
    val rootAccount: String = ""
)
