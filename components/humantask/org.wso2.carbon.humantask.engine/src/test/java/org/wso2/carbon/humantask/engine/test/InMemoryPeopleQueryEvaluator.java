/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/


package org.wso2.carbon.humantask.engine.test;

import org.wso2.carbon.humantask.engine.runtime.PeopleQueryEvaluator;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InMemoryPeopleQueryEvaluator implements PeopleQueryEvaluator {

    Map<String, User> users;

    public InMemoryPeopleQueryEvaluator() {
        this.users = new HashMap<>();

        // Alex in all 3 groups.
        List<String> all = new ArrayList<>();
        all.add("group1");
        all.add("group2");
        all.add("group3");
        User alex = new User("alex", all);
        users.put(alex.getName(), alex);

        // Bob and peter in both group 1 and group 2
        List<String> oneAndTwo = new ArrayList<>();
        oneAndTwo.add("group1");
        oneAndTwo.add("group2");
        User bob = new User("bob", oneAndTwo);
        User peter = new User("peter", oneAndTwo);
        users.put(bob.getName(), bob);
        users.put(peter.getName(), peter);

        // Michel and Jena in both group 2 and group 3
        List<String> twoAndThree = new ArrayList<>();
        twoAndThree.add("group2");
        twoAndThree.add("group3");
        User michel = new User("michel", twoAndThree);
        User jena = new User("jena", twoAndThree);
        users.put(michel.getName(), michel);
        users.put(jena.getName(), jena);

        // Green and Steve in both group 1 and group 3
        List<String> oneAndThree = new ArrayList<>();
        oneAndThree.add("group1");
        oneAndThree.add("group3");
        User green = new User("green", oneAndThree);
        User steve = new User("steve", oneAndThree);
        users.put(green.getName(), green);
        users.put(steve.getName(), steve);

        // Rick only in group 1
        List<String> one = new ArrayList<>();
        one.add("group1");
        User rick = new User("rick", one);
        users.put(rick.getName(), rick);

        // Smith only in group 2
        List<String> two = new ArrayList<>();
        two.add("group2");
        User smith = new User("smith", two);
        users.put(smith.getName(), smith);

        // Frank only in group 3
        List<String> three = new ArrayList<>();
        three.add("group3");
        User frank = new User("frank", three);
        users.put(frank.getName(), frank);

    }

    @Override
    public boolean isExistingUser(String userName) {
        return this.users.containsKey(userName);
    }

    @Override
    public boolean isExistingGroup(String groupName) {
        throw new UnsupportedOperationException("Not Implemented Yet.");
    }

    @Override
    public List<String> getGroupsOfUser(String userName) {
        return this.users.get(userName).groups;
    }


    class User {
        private String name;
        private List<String> groups;

        public User(final String name, List<String> groups) {
            this.name = name;
            this.groups = groups;
        }

        public List<String> getGroups() {
            return groups;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User user = (User) o;
            return name.equals(user.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }


}
