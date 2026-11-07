export function KingMovement(from, to) {
    return Math.abs(to.x - from.x) === 2 && to.y === from.y;
}