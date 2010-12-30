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
package springobjectmapper.model;

import springobjectmapper.Id;

public class Person {
    @Id
    private Long id;
    private final String firstName;
    private final String lastName;
    private String email;
    private final Long countryId;

    public Person(String firstName, String lastName, String email, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.countryId = country.id();
    }

    public Long id() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }
}
