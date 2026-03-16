@startuml
interface CombatNode {
+getName(): String
+getHealth(): int
+getAttackPower(): int
+takeDamage(amount: int): void
+isAlive(): boolean
+getChildren(): List<CombatNode>
+printTree(indent: String): void
}

abstract class UnitLeaf {
-name: String
-health: int
-attackPower: int
+getName(): String
+getHealth(): int
+getAttackPower(): int
+takeDamage(amount: int): void
+isAlive(): boolean
+getChildren(): List<CombatNode>
+printTree(indent: String): void
}

class HeroUnit
class EnemyUnit

class PartyComposite {
-name: String
-children: List<CombatNode>
+add(node: CombatNode): void
+remove(node: CombatNode): void
+getName(): String
+getHealth(): int
+getAttackPower(): int
+takeDamage(amount: int): void
+isAlive(): boolean
+getChildren(): List<CombatNode>
+printTree(indent: String): void
}

class RaidGroup

CombatNode <|.. UnitLeaf
CombatNode <|.. PartyComposite

UnitLeaf <|-- HeroUnit
UnitLeaf <|-- EnemyUnit
PartyComposite <|-- RaidGroup

PartyComposite o-- CombatNode
@enduml