export function bishopMovement(from, to) {
    return Math.abs(to.x - from.x) ===
        Math.abs(to.y - from.y);
}