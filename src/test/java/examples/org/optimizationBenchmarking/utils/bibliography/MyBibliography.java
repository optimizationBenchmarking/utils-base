package examples.org.optimizationBenchmarking.utils.bibliography;

import java.util.Random;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticleBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibBookBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganizationBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReportBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;

/**
 * A class to test generating some of my own papers with the
 * {@link org.optimizationBenchmarking.utils.bibliography.data bibliography
 * API}.
 */
public final class MyBibliography extends BibliographyExample {

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  @Override
  public final Bibliography call() {

    try (final BibliographyBuilder bb = new BibliographyBuilder()) {

      MyBibliography.__WWTWDY2014FFA(bb);
      MyBibliography.__WWTY2014EEIAWGP(bb);
      MyBibliography.__PROC2013IDEAL(bb);
      MyBibliography.__DWT2011ASOSRFEOOGS(bb);
      MyBibliography.__TYW2012SSOECFLSGO(bb);
      MyBibliography.__CWM2011VOEAFRWA(bb);
      MyBibliography.__WZKG2009DGPFz(bb);
      MyBibliography.__WZKG2007DGPFg(bb);

      return bb.getResult();
    }
  }

  /**
   * add a random own publications
   *
   * @param bb
   *          the builder
   * @param r
   *          the randomizer
   */
  static final void __addRandomOfMine(final BibliographyBuilder bb,
      final Random r) {
    switch (r.nextInt(8)) {
      case 0: {
        MyBibliography.__WWTWDY2014FFA(bb);
        return;
      }
      case 1: {
        MyBibliography.__WWTY2014EEIAWGP(bb);
        return;
      }
      case 2: {
        MyBibliography.__PROC2013IDEAL(bb);
        return;
      }
      case 3: {
        MyBibliography.__DWT2011ASOSRFEOOGS(bb);
        return;
      }
      case 4: {
        MyBibliography.__TYW2012SSOECFLSGO(bb);
        return;
      }
      case 5: {
        MyBibliography.__CWM2011VOEAFRWA(bb);
        return;
      }
      case 6: {
        MyBibliography.__WZKG2009DGPFz(bb);
        return;
      }
      default: {
        MyBibliography.__WZKG2007DGPFg(bb);
        return;
      }
    }
  }

  /**
   * make paper TYW2012SSOECFLSGO
   *
   * @param bb
   *          the builder
   */
  private static final void __TYW2012SSOECFLSGO(
      final BibliographyBuilder bb) {
    try (final BibTechReportBuilder trb = bb.techReport()) {
      trb.setTitle(
          "Special Session on Evolutionary Computation for Large Scale Global Optimization at 2012 IEEE World Congress on Computational Intelligence (CEC@WCCI-2012)"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = trb.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Tang"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u5510\u8F72"); //$NON-NLS-1$
          ab.setPersonalName("Ke"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Yang"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u6768\u632F\u5B87"); //$NON-NLS-1$
          ab.setPersonalName("Zhenyu"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
      }
      try (final BibOrganizationBuilder ob = trb.publisher()) {
        ob.setAddress("Hefei, Anhui, China"); //$NON-NLS-1$
        ob.setName(
            "University of Science and Technology of China (USTC), School of Computer Science and Technology (SCST), Nature Inspired Computation and Applications Laboratory (NICAL)"); //$NON-NLS-1$
      }
      try (final BibDateBuilder db = trb.date()) {
        db.setYear(2012);
        db.setMonth(EBibMonth.JUNE);
        db.setDay(14);
      }
      trb.setURL(
          "http://www.it-weise.de/documents/files/TYW2012SSOECFLSGO.pdf"); //$NON-NLS-1$
    }
  }

  /**
   * make paper WWTWDY2014FFA
   *
   * @param bb
   *          the builder
   */
  private static final void __WWTWDY2014FFA(final BibliographyBuilder bb) {

    try (final BibArticleBuilder bab = bb.article()) {
      bab.setTitle("Frequency Fitness Assignment"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Wan"); //$NON-NLS-1$
          ab.setPersonalName("Mingxu"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u4E07\u660E\u7EEA"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Tang"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u5510\u8F72"); //$NON-NLS-1$
          ab.setPersonalName("Ke"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Wang"); //$NON-NLS-1$
          ab.setPersonalName("Pu"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Devert"); //$NON-NLS-1$
          ab.setPersonalName("Alexandre"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setOriginalSpelling("\u59DA\u65B0"); //$NON-NLS-1$
          ab.setFamilyName("Yao"); //$NON-NLS-1$
          ab.setPersonalName("Xin"); //$NON-NLS-1$
        }
      }

      bab.setJournal(
          "IEEE Transactions on Evolutionary Computation (IEEE-TEVC)"); //$NON-NLS-1$
      bab.setDOI("10.1109/TEVC.2013.2251885"); //$NON-NLS-1$
      bab.setVolume("18"); //$NON-NLS-1$
      bab.setNumber("2"); //$NON-NLS-1$
      bab.setStartPage("226"); //$NON-NLS-1$
      bab.setEndPage("243"); //$NON-NLS-1$

      try (final BibDateBuilder db = bab.date()) {
        db.setYear(2014);
        db.setMonth(EBibMonth.APRIL);
      }
    }
  }

  /**
   * make paper DWT2011ASOSRFEOOGS
   *
   * @param bb
   *          the builder
   */
  private static final void __DWT2011ASOSRFEOOGS(
      final BibliographyBuilder bb) {

    try (final BibArticleBuilder bab = bb.article()) {
      bab.setTitle(
          "A Study on Scalable Representations for Evolutionary Optimization of Ground Structures"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Devert"); //$NON-NLS-1$
          ab.setPersonalName("Alexandre"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Tang"); //$NON-NLS-1$
          ab.setPersonalName("Ke"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u5510\u8F72"); //$NON-NLS-1$
        }
      }

      bab.setJournal("Evolutionary Computation"); //$NON-NLS-1$
      bab.setDOI("10.1162/EVCO_a_00054"); //$NON-NLS-1$
      bab.setVolume("20"); //$NON-NLS-1$
      bab.setNumber("3"); //$NON-NLS-1$
      bab.setStartPage("453"); //$NON-NLS-1$
      bab.setEndPage("472"); //$NON-NLS-1$

      try (final BibDateBuilder db = bab.date()) {
        db.setYear(2012);
        db.setQuarter(EBibQuarter.FALL);
      }
      try (final BibOrganizationBuilder bo = bab.publisher()) {
        bo.setName("MIT Press"); //$NON-NLS-1$
        bo.setAddress("Cambridge, MA, USA"); //$NON-NLS-1$
      }
    }
  }

  /**
   * make paper WZKG2009DGPFz
   *
   * @param bb
   *          the builder
   */
  private static final void __WZKG2009DGPFz(final BibliographyBuilder bb) {

    try (final BibArticleBuilder bab = bb.article()) {
      bab.setTitle(
          "Combining Genetic Programming and Model-Driven Development"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Zapf"); //$NON-NLS-1$
          ab.setPersonalName("Michael"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Khan"); //$NON-NLS-1$
          ab.setPersonalName("Mohammad Ullah"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Geihs"); //$NON-NLS-1$
          ab.setPersonalName("Kurt"); //$NON-NLS-1$
        }
      }

      bab.setJournal(
          "International Journal of Computational Intelligence and Applications (IJCIA)"); //$NON-NLS-1$
      bab.setDOI("10.1142/S1469026809002436"); //$NON-NLS-1$
      bab.setURL(
          "http://www.it-weise.de/documents/files/WZKG2009DGPFz.pdf"); //$NON-NLS-1$
      bab.setVolume("8"); //$NON-NLS-1$
      bab.setNumber("1"); //$NON-NLS-1$
      bab.setStartPage("37"); //$NON-NLS-1$
      bab.setEndPage("52"); //$NON-NLS-1$
      try (final BibOrganizationBuilder ob = bab.publisher()) {
        ob.setAddress("London, UK"); //$NON-NLS-1$
        ob.setName("Imperial College Press Co"); //$NON-NLS-1$
      }

      try (final BibDateBuilder db = bab.date()) {
        db.setYear(2009);
        db.setMonth(EBibMonth.MARCH);
      }
    }
  }

  /**
   * make paper WZKG2007DGPFg
   *
   * @param bb
   *          the builder
   */
  private static final void __WZKG2007DGPFg(final BibliographyBuilder bb) {

    try (final BibInProceedingsBuilder bab = bb.inProceedings()) {
      bab.setTitle("Genetic Programming meets Model-Driven Development"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Zapf"); //$NON-NLS-1$
          ab.setPersonalName("Michael"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Khan"); //$NON-NLS-1$
          ab.setPersonalName("Mohammad Ullah"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Geihs"); //$NON-NLS-1$
          ab.setPersonalName("Kurt"); //$NON-NLS-1$
        }
      }

      try (final BibProceedingsBuilder pb = bab.proceedings()) {
        pb.setTitle(
            "Proceedings of the 7th International Conference on Hybrid Intelligent Systems (HIS'07)"); //$NON-NLS-1$
        try (final BibAuthorsBuilder abs = pb.setEditors()) {
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("K\u00f6nig"); //$NON-NLS-1$
            ab.setPersonalName("Andreas"); //$NON-NLS-1$
          }
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("K\u00f6ppen"); //$NON-NLS-1$
            ab.setPersonalName("Mario"); //$NON-NLS-1$
          }
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("Abraham"); //$NON-NLS-1$
            ab.setPersonalName("Ajith"); //$NON-NLS-1$
          }
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("Igel"); //$NON-NLS-1$
            ab.setPersonalName("Christian"); //$NON-NLS-1$
          }
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("Kasabov"); //$NON-NLS-1$
            ab.setPersonalName("Nikola"); //$NON-NLS-1$
          }
        }
        try (final BibOrganizationBuilder bo = pb.publisher()) {
          bo.setName("IEEE Computer Society"); //$NON-NLS-1$
          bo.setAddress("Piscataway, NJ, USA"); //$NON-NLS-1$
        }
        try (final BibOrganizationBuilder bo = pb.location()) {
          bo.setName("Fraunhofer Center FhG ITWM/FhG IESE"); //$NON-NLS-1$
          bo.setAddress("Kaiserslautern, Germany"); //$NON-NLS-1$
        }
        try (final BibDateBuilder d = pb.startDate()) {
          d.setYear(2007);
          d.setMonth(EBibMonth.SEPTEMBER);
          d.setDay(17);
        }
        try (final BibDateBuilder d = pb.endDate()) {
          d.setYear(2007);
          d.setMonth(EBibMonth.SEPTEMBER);
          d.setDay(19);

        }
      }

      bab.setDOI("10.1109/HIS.2007.11"); //$NON-NLS-1$
      bab.setURL(
          "http://www.it-weise.de/documents/files/WZKG2007DGPFg.pdf"); //$NON-NLS-1$
      bab.setStartPage("332"); //$NON-NLS-1$
      bab.setEndPage("335"); //$NON-NLS-1$
    }
  }

  /**
   * make paper WWTY2014EEIAWGP
   *
   * @param bb
   *          the builder
   */
  private static final void __WWTY2014EEIAWGP(
      final BibliographyBuilder bb) {

    try (final BibInProceedingsBuilder bab = bb.inProceedings()) {
      bab.setTitle(
          "Evolving Exact Integer Algorithms with Genetic Programming"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Wan"); //$NON-NLS-1$
          ab.setPersonalName("Mingxu"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u4E07\u660E\u7EEA"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Tang"); //$NON-NLS-1$
          ab.setPersonalName("Ke"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u5510\u8F72"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Yao"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u59DA\u65B0"); //$NON-NLS-1$
          ab.setPersonalName("Xin"); //$NON-NLS-1$
        }
      }

      try (final BibProceedingsBuilder pb = bab.proceedings()) {
        pb.setTitle(
            "Proceedings of the IEEE Congress on Evolutionary Computation (CEC'14), Proceedings of the 2014 World Congress on Computational Intelligence (WCCI'14)"); //$NON-NLS-1$
        try (final BibOrganizationBuilder bo = pb.publisher()) {
          bo.setName("IEEE Computer Society Press"); //$NON-NLS-1$
          bo.setAddress("Los Alamitos, CA, USA"); //$NON-NLS-1$
        }
        try (final BibOrganizationBuilder bo = pb.location()) {
          bo.setName("Beijing International Convention Center (BICC)"); //$NON-NLS-1$
          bo.setAddress("Beijing, China"); //$NON-NLS-1$
        }
        try (final BibDateBuilder d = pb.startDate()) {
          d.setYear(2014);
          d.setMonth(EBibMonth.JUNE);
          d.setDay(6);
        }
        try (final BibDateBuilder d = pb.endDate()) {
          d.setYear(2014);
          d.setMonth(EBibMonth.JUNE);
          d.setDay(11);
        }
      }

      bab.setStartPage("1816"); //$NON-NLS-1$
      bab.setEndPage("1823"); //$NON-NLS-1$
    }
  }

  /**
   * make paper PROC2013IDEAL
   *
   * @param bb
   *          the builder
   */
  private static final void __PROC2013IDEAL(final BibliographyBuilder bb) {
    try (final BibProceedingsBuilder pb = bb.proceedings()) {
      pb.setTitle(
          "Proceedings of the 14th International Conference on Intelligent Data Engineering and Automated Learning (IDEAL'13)"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = pb.setEditors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Yin"); //$NON-NLS-1$
          ab.setPersonalName("Hujun"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Tang"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u5510\u8F72"); //$NON-NLS-1$
          ab.setPersonalName("Ke"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Gao"); //$NON-NLS-1$
          ab.setPersonalName("Yang"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Klawonn"); //$NON-NLS-1$
          ab.setPersonalName("Frank"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Lee"); //$NON-NLS-1$
          ab.setPersonalName("Minho"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Li"); //$NON-NLS-1$
          ab.setPersonalName("Bin"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Yao"); //$NON-NLS-1$
          ab.setOriginalSpelling("\u59DA\u65B0"); //$NON-NLS-1$
          ab.setPersonalName("Xin"); //$NON-NLS-1$
        }
      }
      try (final BibOrganizationBuilder bo = pb.publisher()) {
        bo.setName("Springer-Verlag GmbH"); //$NON-NLS-1$
        bo.setAddress("Berlin, Germany"); //$NON-NLS-1$
      }
      try (final BibOrganizationBuilder bo = pb.location()) {
        bo.setName("Empark Grand Hotel"); //$NON-NLS-1$
        bo.setAddress("Hefei, Anhui, China"); //$NON-NLS-1$
      }
      try (final BibDateBuilder d = pb.startDate()) {
        d.setYear(2013);
        d.setMonth(EBibMonth.OCTOBER);
        d.setDay(20);
      }
      try (final BibDateBuilder d = pb.endDate()) {
        d.setYear(2013);
        d.setMonth(EBibMonth.OCTOBER);
        d.setDay(23);
      }
      pb.setSeries("Lecture Notes in Computer Science (LNCS)"); //$NON-NLS-1$
      pb.setVolume("8206/2013"); //$NON-NLS-1$
      pb.setDOI("10.1007/978-3-642-41278-3"); //$NON-NLS-1$
      pb.setISBN("978-3-642-41277-6"); //$NON-NLS-1$
    }
  }

  /**
   * make paper CWM2011VOEAFRWA
   *
   * @param bb
   *          the builder
   */
  private static final void __CWM2011VOEAFRWA(
      final BibliographyBuilder bb) {
    try (final BibBookBuilder pb = bb.book()) {
      pb.setTitle(
          "Variants of Evolutionary Algorithms for Real-World Applications"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = pb.setEditors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Chiong"); //$NON-NLS-1$
          ab.setPersonalName("Raymond"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Weise"); //$NON-NLS-1$
          ab.setPersonalName("Thomas"); //$NON-NLS-1$
        }
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("Michalewicz"); //$NON-NLS-1$
          ab.setPersonalName("Zbigniew"); //$NON-NLS-1$
        }
      }
      try (final BibOrganizationBuilder bo = pb.publisher()) {
        bo.setName("Springer-Verlag GmbH"); //$NON-NLS-1$
        bo.setAddress("Berlin/Heidelberg"); //$NON-NLS-1$
      }
      try (final BibDateBuilder d = pb.date()) {
        d.setYear(2011);
        d.setMonth(EBibMonth.SEPTEMBER);
        d.setDay(30);
      }
      pb.setDOI("10.1007/978-3-642-23424-8"); //$NON-NLS-1$
      pb.setISBN("978-3-642-23424-8"); //$NON-NLS-1$
    }
  }

  /** the constructor */
  public MyBibliography() {
    super();
  }
}
