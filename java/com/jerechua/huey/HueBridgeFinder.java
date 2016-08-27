package com.jerechua.huey;

import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;

/** Utility to find bridges easier. */
final class HueBridgeFinder {

  public static HueBridgeFinder newFinder(PHHueSDK sdk) {
    return new HueBridgeFinder().setSDK(sdk);
  }

  private PHHueSDK sdk;
  private boolean searchUpnp = false;
  private boolean searchPortal = false;
  private boolean searchPortalAddress = false;
  private String portalAddress;

  public HueBridgeFinder setSDK(PHHueSDK sdk) {
    this.sdk = sdk;
    return this;
  }

  public HueBridgeFinder searchUPNP(boolean searchUpnp) {
    this.searchUpnp = searchUpnp;
    return this;
  }

  public HueBridgeFinder searchPortal(boolean searchPortal) {
    this.searchPortal = searchPortal;
    return this;
  }

  public HueBridgeFinder searchPortalAddress(boolean searchPortalAddress) {
    this.searchPortalAddress = searchPortalAddress;
    return this;
  }

  public HueBridgeFinder setPortalAddress(String portalAddress) {
    this.portalAddress = portalAddress;
    return this;
  }

  public void find() {
    if (sdk == null) {
      throw new RuntimeException("HueBridgeFinder SDK must be present");
    }
    if (searchPortalAddress && portalAddress == null) {
      throw new RuntimeException("ip address must be set when using searchPortalAddress(true)");
    }
    PHBridgeSearchManager searchManager
      = (PHBridgeSearchManager)sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
    searchManager.search(searchUpnp, searchPortal, searchPortalAddress);
  }
}