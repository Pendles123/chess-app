export function QueenMovement(from, to) {
    // Lateral movement
    if (to.y == from.y && to.x != from.x) {
        return true;
    }
    if (to.x == from.x && to.y != from.y) {
        return true;
    }
    return Math.abs(to.x - from.x) >= 1 && Math.abs(to.y - from.y) >= 1;
}