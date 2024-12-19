package com.acieslinski.videos.featues.videos.list.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import com.acieslinski.videos.videos.test.R
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class FragmentTestRule<T : Fragment>(
    private val fragmentClass: Class<T>,
    private val fragmentArgs: Bundle? = null,
) : TestRule {

    private lateinit var activityScenario: ActivityScenario<FragmentTestActivity>
    lateinit var activity: FragmentTestActivity
        private set

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                activityScenario = ActivityScenario.launch(FragmentTestActivity::class.java)
                activityScenario.onActivity { activity ->
                    this@FragmentTestRule.activity = activity

                    val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
                        fragmentClass.classLoader!!,
                        fragmentClass.name
                    )
                    fragment.arguments = fragmentArgs
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.test_container, fragment)
                        .commitNow()
                }

                try {
                    base.evaluate()
                } finally {
                    activityScenario.close()
                }
            }
        }
    }
}
