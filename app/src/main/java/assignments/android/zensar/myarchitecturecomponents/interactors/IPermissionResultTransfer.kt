package assignments.android.zensar.myarchitecturecomponents.interactors

import android.content.Context

/**
 * Created by RK51670 on 31-01-2018.
 */
interface IPermissionResultTransfer {

    fun setResultCode(code: Int, context: Context)
}