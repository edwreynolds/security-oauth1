package com.epr.util;

/**
 * This class is used to contain web app version information.  
 * The version format is based on Apache Directory project.
 * <p>
 * Version format: Major.Minor.Micro.SVN {STATE}
 * <p>
 * <B>Major</B>: Increased when a significant new feature is added (significant 
 * value added) or if new version is not compatible with previous release.  
 * Not compatible for instance because some hardware or software component must 
 * be added to underlying system or there are incompatible changes in the API.
 * <p>
 * <B>Minor</B>: Increases when a new feature is introduced.
 * <p>
 * <B>Micro</B>: Increases when a bug or a trivial change / enhancement is made. 
 * <p>
 * <B>SVN</B>: An optional field that is the revision in the SVN source 
 * control system.
 * <p>
 * And an optional <B>state</B> label that indicates the maturity of a release:
 *    <B>M (Milestone)</B> means the feature set can change at any time in the 
 *    next milestone releases.  The last milestone release becomes the first 
 *    release candidate.
 *    
 *    <B>RC (Release Candidate)</B> means the feature set is frozen and the next 
 *    RC releases will focus on fixing problems unless there is a serious 
 *    flaw in design. The last release candidate becomes the first GA 
 *    release.
 *    
 *    <B>No label implies GA (General Availability)</B>, which means the release 
 *    is stable enough and therefore ready for production environment.  
 * 
 * 
 */
public class VersionInfo {
	
    public static final String APP_VERSION_NBR = "1.1.0";
    public static final String RESOURCE_VERSION_NBR = APP_VERSION_NBR.replaceAll("\\.", "_").replaceAll(" ", "-");
	
    private VersionInfo() {}
}
