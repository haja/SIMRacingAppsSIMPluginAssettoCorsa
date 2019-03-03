/**
 * 
 */
package com.SIMRacingApps.SIMPlugins.AssettoCorsa;

import com.SIMRacingApps.SIMPlugin;
import com.SIMRacingApps.Server;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class AssettoCorsaSIMPlugin extends SIMPlugin {

    private PhysicsMemory currentPhysics;

    /**
     * @throws SIMPluginException
     */
    public AssettoCorsaSIMPlugin() throws SIMPluginException {
        Server.logger().info("AssettoCorsaSIMPluging created");
    }

}
