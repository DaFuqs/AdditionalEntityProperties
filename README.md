# AdditionalEntityAttributes

Adds a list of new attributes for entities and players.
These attributes allow mod compatibility and serve as an API for mods that make use of them.

## Content

### Attributes

- `WATER_SPEED` (living entities): Controls the speed of the player when in water
- `LAVA_SPEED` (living entities): Controls the player's speed when in lava
- `JUMP_HEIGHT` (living entities): Controls the jump height of the player
- `DROPPED_EXPERIENCE` (living entities): Changes the amount of experience dropped from mining blocks and killing mobs
- `MAGIC_PROTECTION` (living entities): Reduces the amount of magic damage taken
- `WIDTH` (living entities): Controls the player's width
- `HEIGHT` (living entities): Controls the player's height
- `HITBOX_SCALE` (living entities): Controls the player's hitbox scale
- `HITBOX_WIDTH` (living entities): Controls the player's hitbox width
- `HITBOX_HEIGHT` (living entities): Controls the player's hitbox height
- `MODEL_SCALE` (living entities): Controls the player's model scale
- `MODEL_WIDTH` (living entities): Controls the player's model width
- `HITBOX_HEIGHT` (living entities): Controls the player's model height
- `MOB_DETECTION_RANGE` (living entities): Controls the range that the player can be detected by hostile mobs
- `WATER_VISIBILITY` (player only): Controls the player's visibility in water by adjusting the fog distance
- `LAVA_VISIBILITY` (player only): Controls the player's visibility in lava by adjusting the fog distance
- `CRITICAL_BONUS_DAMAGE` (player only): Controls the bonus damage dealt when critical hits are made
- `DIG_SPEED` (player only): Controls the player's digging speed
- `BONUS_LOOT_COUNT_ROLLS` (player only): Controls the number of drops the player receives when using enchantments such
  as Loot and Luck
- `BONUS_RARE_LOOT_ROLLS` (player only): The number of times the chance-based loot table is rolled
- `COLLECTION_RANGE` (player only): Controls the distance where the player is able to collect ("touch") entity types in
  the `additionalentityattributes:affected_by_collection_range` tag (like items & experience orbs)

### Entity Type Tags

- `additionalentityattributes:affected_by_collection_range`: Marks an entity type as being affected by the collection range attribute (by default items & experience orbs).

## Usage

### Players
You will probably not need to install this library yourself, as most mods that use it will ship with it. If you want to use the above attributes via commands or anything else, feel free to do so, of course.

### @Developers
Feel free to JIJ.
You can fetch all builds from the Modrinth Maven:

```
repositories {
	exclusiveContent {
		forRepository {
			maven {
				name = "Modrinth"
				url = "https://api.modrinth.com/maven"
			}
		}
		filter {
			includeGroup "maven.modrinth"
		}
	}
}
```

```
dependencies {
	modImplementation include("maven.modrinth:AdditionalEntityAttributes:<version>")
}
```
