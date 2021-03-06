/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.tagging.de;

import org.languagetool.AnalyzedToken;
import org.languagetool.tagging.de.GermanToken.Genus;
import org.languagetool.tagging.de.GermanToken.Kasus;
import org.languagetool.tagging.de.GermanToken.Numerus;
import org.languagetool.tagging.de.GermanToken.POSType;
import org.languagetool.tagging.de.GermanToken.Determination;

/**
 * One reading of a German word. Many words can have more
 * than one reading, e.g. "Tische" can be both Nominativ Plural
 * and Genitiv Plural (among other readings).
 * 
 * @author Daniel Naber
 */
public class AnalyzedGermanToken {

  private final POSType type;
  private final Kasus casus;
  private final Numerus numerus;
  private final Genus genus;
  private final Determination determination;

  public AnalyzedGermanToken(AnalyzedToken token) {
    String posTag = token.getPOSTag();
    if (posTag == null || posTag.split(":").length < 3) {
      type = null;
      casus = null;
      numerus = null;
      genus = null;
      determination = null;
      return;
    }
    String[] parts = posTag.split(":");
    POSType tempType = null;
    Kasus tempCasus = null;
    Numerus tempNumerus = null;
    Genus tempGenus = null;
    Determination tempDetermination = null;
    for (String part : parts) {
      if (part.equals("EIG")) {
        tempType = POSType.PROPER_NOUN;
      } else if (part.equals("SUB") && tempType == null) {
        tempType = POSType.NOMEN;
      } else if (part.equals("PA1") || part.equals("PA2")) {
        tempType = POSType.PARTIZIP;
      } else if (part.equals("VER") && tempType == null) {
        tempType = POSType.VERB;
      } else if (part.equals("ADJ") && tempType == null) {
        tempType = POSType.ADJEKTIV;
      } else if (part.equals("PRO") && tempType == null) {
        tempType = POSType.PRONOMEN;
      } else if (part.equals("ART") && tempType == null) {
        tempType = POSType.DETERMINER;
      } else if (part.equals("AKK")) {
        tempCasus = Kasus.AKKUSATIV;
      } else if (part.equals("GEN")) {
        tempCasus = Kasus.GENITIV;
      } else if (part.equals("NOM")) {
        tempCasus = Kasus.NOMINATIV;
      } else if (part.equals("DAT")) {
        tempCasus = Kasus.DATIV;
      } else if (part.equals("PLU")) {
        tempNumerus = Numerus.PLURAL;
      } else if (part.equals("SIN")) {
        tempNumerus = Numerus.SINGULAR;
      } else if (part.equals("MAS")) {
        tempGenus = Genus.MASKULINUM;
      } else if (part.equals("FEM")) {
        tempGenus = Genus.FEMININUM;
      } else if (part.equals("NEU")) {
        tempGenus = Genus.NEUTRUM;
      } else if (part.equals("NOG")) {
        tempGenus = Genus.FEMININUM;    // NOG = no genus because only used as plural
      } else if (part.equals("ALG")) {
        tempGenus = Genus.ALLGEMEIN;
      } else if (part.equals("IND")) {
        tempDetermination = Determination.INDEFINITE;
      } else if (part.equals("DEF")) {
        tempDetermination = Determination.DEFINITE;
      }
    }
    type = tempType != null ? tempType : null;
    casus = tempCasus != null ? tempCasus : null;
    numerus = tempNumerus != null ? tempNumerus : null;
    genus = tempGenus != null ? tempGenus : null;
    determination = tempDetermination != null ? tempDetermination : null;
  }

  public POSType getType() {
    return type; 
  }

  public Kasus getCasus() {
    return casus; 
  }

  public Numerus getNumerus() {
    return numerus; 
  }

  public Genus getGenus() {
    return genus; 
  }
  
  /** @since 3.2 */
  public Determination getDetermination() {
    return determination;
  }
  
}
