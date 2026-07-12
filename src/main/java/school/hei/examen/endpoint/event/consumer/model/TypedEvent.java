package school.hei.examen.endpoint.event.consumer.model;

import school.hei.examen.PojaGenerated;
import school.hei.examen.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
