### CapesM is a plugin for Spigot which can transform any banner to a cape.

# Commands:
**/cape set** - Set banner from your hand as a cape. *Needed permission: `capes.set`.*

**/cape remove** - Remove the cape. *Needed permission: `capes.remove`.*

**/cape reload** - Reload config. *Needed permission: `capes.reload`.*

# CapesAPI
You can use this plugin as a library in your project. Just import class `leonidm.capes.CapesAPI` and call such static functions as:
- **CapesAPI.setCape(Player player, ItemStack banner)** - set given ItemStack as a Player's cape.
- **CapesAPI.removeCape(Player player)** - Remove Player's cape *(returns `true` if cape was removed, `false` if not)*.
- **CapesAPI.hasCape(Player player)** - Check if Player has any cape *(returns `true` if Player has any cape, `false` if not)*.
- **CapesAPI.getCape(Player player)** - Get Player's cape *(returns `ItemStack` if Player has any cape, `null` if not)*.
