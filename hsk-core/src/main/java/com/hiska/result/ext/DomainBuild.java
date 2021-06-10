/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © 2020 HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiska.result.ext;

import com.hiska.result.Domain.Type;
import com.hiska.result.*;
import java.util.Collection;

/**
 * @author yracnet
 */
public class DomainBuild {
   public static DomainBuild create() {
      return new DomainBuild();
   }

   public static DomainBuild create(String code) {
      return new DomainBuild(code);
   }

   private String code;
   private MapItem<Domain> map = new MapItem<>();

   public DomainBuild() {
   }

   public DomainBuild(String code) {
      this.code = code;
   }

   public DomainBuild code(String code) {
      this.code = code;
      return this;
   }

   public DomainBuild dominio(String name, String code, String classifier, Type type) {
      map.put(name, new Domain(code, classifier, type));
      return this;
   }

   public DomainBuild classifier(String name, String classifier, Type type) {
      map.put(name, Domain.of(code, classifier, type));
      return this;
   }

   public DomainBuild classifierList(String name, String classifier) {
      map.put(name, Domain.of(code, classifier, Type.LIST));
      return this;
   }

   public DomainBuild classifierTree(String name, String classifier) {
      map.put(name, Domain.of(code, classifier, Type.TREE));
      return this;
   }

   public DomainBuild domainList(String name, String code) {
      map.put(name, Domain.of(code, Type.LIST));
      return this;
   }

   public DomainBuild domainTree(String name, String code) {
      map.put(name, Domain.of(code, Type.TREE));
      return this;
   }

   public MapItem<Domain> get() {
      return map;
   }

   public Collection<Domain> values() {
      return map.values();
   }

   public Collection<String> keys() {
      return map.keySet();
   }

   public Domain get(String name) {
      return map.get(name);
   }
}
