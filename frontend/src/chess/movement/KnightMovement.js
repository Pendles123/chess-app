export function KnightMovement(from, to) {
    if (Math.abs(to.x - from.x) === 1 && Math.abs(to.y - from.y) === 2) {
        return true;
    } else return Math.abs(to.x - from.x) === 2 && Math.abs(to.y - from.y) === 1;
}