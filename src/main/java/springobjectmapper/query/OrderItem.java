/*
 * Copyright 2010 Janne Hietamaki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package springobjectmapper.query;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private final String key;
    private boolean descending = false;

    public OrderItem(String key) {
        this.key = key;
    }

    public void setDescending(boolean value) {
        descending = value;
    }

    public String key() {
        return key;
    }

    public boolean descending() {
        return descending;
    }
}