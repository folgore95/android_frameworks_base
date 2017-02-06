/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.android.systemui.utils.leaks;

import android.content.Context;

import com.android.systemui.tuner.TunerService;

public class FakeTunerService extends TunerService {

    private final BaseLeakChecker<Tunable> mBaseLeakChecker;

    public FakeTunerService(Context context, LeakCheckedTest test) {
        super(context);
        mBaseLeakChecker = new BaseLeakChecker<>(test, "tunable");
        destroy();
    }

    @Override
    public void addTunable(Tunable tunable, String... keys) {
        for (String key : keys) {
            tunable.onTuningChanged(key, null);
        }
        mBaseLeakChecker.addCallback(tunable);
    }

    @Override
    public void removeTunable(Tunable tunable) {
        mBaseLeakChecker.removeCallback(tunable);
    }
}
