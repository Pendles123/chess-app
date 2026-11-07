export function RookMovement(from, to) {
    // Lateral movement
    if (to.y === from.y && to.x !== from.x) {
        return true;
    }
    return to.x === from.x && to.y !== from.y;
}